# seckill

基于Spring Cloud + Spring Cloud Alibaba + MySQL 分库分表+ Redis + RabbitMQ 开发的高并发商品限时秒杀系统

## 前言

本项目分为用户注册登录、秒杀商品管理模块。注册登录功能目前使用分布式session+cookie完成权限验证，支持保持及延长用户登录状态。 此项目整体采用Spring MVC+RESTFUL风格，mybatis持久层框架，采用Spring Cloud及Spring Cloud Alibaba实现服务分布式服务调用，服务注册发现使用Nacos ，服务调用使用OpenFeign，分布式事务使用Seata，熔断降级使用Sentinel,网关使用Gateway

## 技术组件

### 📌后端技术:

| 技术                   | 名称                      | 官网                                              |
| ---------------------- | ------------------------- | ------------------------------------------------- |
| SpringBoot             | SpringBoot框架            | https://spring.io/projects/spring-boot            |
| MyBatis                | MyBatis                   | https://mybatis.org/mybatis-3/zh/index.html       |
| Redis                  | 分布式缓存数据库          | https://redis.io/                                 |
| RabbitMQ               | 消息队列                  | https://www.rabbitmq.com/                         |
| Maven                  | 项目构建管理              | http://maven.apache.org/                          |
| Swagger2               | 项目API文档生成及测试工具 | http://swagger.io/                                |
| MySQL                  | MySQL数据库               | https://www.mysql.com/                            |
| Sentinel               | Sentinel熔断限流          | https://spring.io/projects/spring-cloud-alibaba   |
| Nacos                  | Nacos服务注册中心         | https://spring.io/projects/spring-cloud-alibaba   |
| Spring Cloud Gateway   | SpringCloud网关组件       | https://spring.io/projects/spring-cloud-gateway   |
| Seata                  | Seata分布式事务           | http://seata.io/zh-cn/index.html                  |
| Spring Cloud OpenFeign | SpringCloud服务调用组件   | https://spring.io/projects/spring-cloud-openfeign |

### 📌前端技术:

| 技术       | 名称        | 官网                             |
| ---------- | ----------- | -------------------------------- |
| Vue        | Vue前段框架 | https://cn.vuejs.org/            |
| Element-ui | VueUi框架   | https://element.eleme.cn/#/zh-CN |

## 项目模块介绍

```
后端
seckill-parent
|--api-gateway                              ||微服务网关
|--seckill-common                           ||项目公共服务
|   |--common-utils                         ||封装统一返回格式
|   |--mvc-config                           ||配置SpringMvc，给接口传参
|   |--service-base                         ||基础配置
|--seckill-goods                            ||秒杀商品信息       
|--seckill-order                            ||秒杀订单信息                    
|--seckill-process                          ||秒杀操作
|--seckill-user                             ||秒杀用户登录
前端src
|--api                                      ||服务调用
|--assets									||静态资源
|--components                               ||组件
|--pages                       				||页面展示
|--router									||前端路由
|--store									||vuex管理
|--utils									||方法管理

```

##  接口设计

### 📌商品列表

商品列表接口查询，商品列表不用登陆即可查，先查询Redis goodsA list，如果goodsA没有缓存，则查询goodsB list，两者查询不到，证明缓存宕机，直接查询数据库

![https://raw.githubusercontent.com/dczeroz/img/master/seckill/goods-list.png](https://raw.githubusercontent.com/dczeroz/img/master/seckill/goods-list.png)

商品列表缓存更新，简单解决单机Redis缓存击穿操作，开启定时任务，先更新goodsB缓存，再更新goodsA缓存。查询缓存时优先查询goodsA，当goodsA查询不到时，表示在更新goodsA缓存，此时goodsB已更新成功查询goodsB，则可避免高并发场景，直接将查询落到数据库上，造成缓存击穿。

![https://raw.githubusercontent.com/dczeroz/img/master/seckill/refresh-redis.png](https://raw.githubusercontent.com/dczeroz/img/master/seckill/refresh-redis.png)

### 📌用户

用户登录接口，将密码进行MD5加密，生成随机唯一值字符串token，将token保存到cookie和Redis中过期时长为一小时，当前端请求后端接口时，将cookie中的token保存在请求头发送，以便后端接收。将用户信息保存在redis中，从redis获取用户信息。

![https://raw.githubusercontent.com/dczeroz/img/master/seckill/login.png](https://raw.githubusercontent.com/dczeroz/img/master/seckill/login.png)

接口传参，使用SpringMVC自定参数解析器，改变SpringMVC的Controller传入参数， SpringMVC框架回调addArgumentResolvers，然后给Controller的参数赋值，获取user过程为从请求头获取token，用token查询Redis中user信息并将传参赋值。在此过程中，将前端cookie和redis中token存放时间再度延长，保证了用户登录体验，延长登录状态。

![https://raw.githubusercontent.com/dczeroz/img/master/seckill/keep-login.png](https://raw.githubusercontent.com/dczeroz/img/master/seckill/keep-login.png)

### 📌网关

网关配置，接下来接口都需要经过网关，统一不画网关。配置跨域处理。判断请求头是否带有token，如果有，用token判断redis是否存在用户信息，如果不存在说明token过期。

![https://raw.githubusercontent.com/dczeroz/img/master/seckill/gateway.png](https://raw.githubusercontent.com/dczeroz/img/master/seckill/gateway.png)

### 📌秒杀

秒杀操作，通过商品id判断秒杀时间是否开始，通过三层缓存保护实现秒杀操作，使用本地缓存作为缓存第一层，使用用户号码和商品id查询Redis，利用Redis预减库存作为缓存第二层保护，RabbitMq异步下单操作作为缓存第三层保护，实现三层缓存保护



异步下单操作，避免超卖问题，判断库存是否大于0。利用乐观锁判断是否减少库存，实现缓存跟数据库同步，添加订单并把订单保存到redis中，避免重复下单去判断redis。如果减少库存失败证明商品秒杀结束，redis做标记结束。



判断库存秒杀结果，前端实现定时器，定时对接口进行查询，判断redis是否有订单信息，如果有返回订单id表示秒杀成功，没则查看redis是否标记商品秒杀结束，标记结束返回1表示秒杀结束，没有标记返回0让前端定时器继续查询。

### 📌订单

查询是否存在订单，用户下单成功，将用户号码及商品id作为key查询Redis，不对数据库直接操作



## 技术实现难点

### 1.分布式session

验证用户账号密码都正确情况下，通过UUID生成唯一id作为token，再将token作为key、用户信息作为value模拟session存储到Redis，同时将token存储到cookie，保存登录状态，当用户访问任一接口，延长用户登录状态避免出现用户在操作时token过期情况。

好处： 在分布式集群情况下，服务器间需要同步，定时同步各个服务器的session信息，会因为延迟到导致session不一致，使用Redis把session数据集中存储起来，解决session不一致问题。解决JWT鉴权无法延长token过期时间，该情况下，token过期时间有redis决定。

### 2.三层缓存保护

描述：通过三级缓冲保护，1、本地标记  2、Redis预处理  3、RabbitMQ异步下单，最后才会访问数据库，减少对数据库的访问。

实现：

1. 在秒杀阶段使用本地标记对Redis库存为0的商品做标记，若被标记过直接返回重复秒杀，未被标记才查询Redis，通过本地标记来减少对Redis的访问

2. 将商品和库存数据同步到Redis中，所有的抢购操作都在Redis中进行处理，通过Redis预减库存减少数据库访问

3. 保护系统不受高流量的冲击而导致数据库崩溃的问题，使用RabbitMQ用异步队列处理下单，实际做了一层缓冲保护，对数据库的访问操作进行控制，避免一时间高并发落在数据库上

   

### 3.解决超卖

1. 对库存更新时，先对库存判断，只有当库存大于0才能更新库存
2. 对用户id和商品id建立一个唯一索引，通过这种约束避免同一用户发同时两个请求秒杀到两件相同商品
3. 实现乐观锁，给商品信息表增加一个version字段，为每一条数据加上版本。每次更新的时候version+1，并且更新时候带上版本号，当提交前版本号等于更新前版本号，说明此时没有被其他线程影响到，正常更新，如果冲突了则不会进行提交更新。当库存是足够的情况下发生乐观锁冲突就进行一定次数的重试。

### 4.商品列表缓存击穿

描述：对商品列表进行缓存处理，定期刷新商品列表缓存，假设刷新商品列表缓存删除时，涌入大量访问，这些访问将会落在数据库上，造成缓存击穿

解决：

在Redis开辟两个list存放商品列表信息，命名为goodsA和goodsB，当访问时优先访问goodsA，如果goodsA访问不到再访问goodsB。定期刷新缓存则相反，先更新goodsB中缓存在更新goodsA中缓存，这样可确保Redis中有商品列表信息，此为单机Redis解决缓存击穿一种方法。