package com.seckill.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.seckill.R;
import com.seckill.RespBeanEnum;
import com.seckill.config.RabbitMQConfig;
import com.seckill.entity.SeckillOrder;
import com.seckill.entity.User;
import com.seckill.exceptionhandler.DongException;
import com.seckill.handle.CustomerBlockHandler;
import com.seckill.service.GoodsService;
import com.seckill.service.OrderService;
import com.seckill.service.ProcessService;
import com.seckill.vo.GoodsVo;
import com.seckill.vo.OrderVo;
import com.seckill.vo.SeckillMessage;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.*;

/**
 * InitializingBean 为bean提供初始化方法
 */
@Service
public class ProcessServiceImpl implements ProcessService,InitializingBean {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderService orderService;

    //做标记，判断该商品是否卖光,目的减少redis访问
    private HashMap<Long, Boolean> goodCountMap = new HashMap<Long, Boolean>();

    @Override
    public void doSeckill(User user, Long goodId) {
        if(user == null)
            throw new DongException(RespBeanEnum.AUTHRNTICATION_FAILED);

        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodId);
        Date date = new Date();
        Date startDate = goodsVo.getStartDate();
        int start = startDate.compareTo(date);
        Date endDate = goodsVo.getEndDate();
        int end = endDate.compareTo(date);
        if(start > 0)
            throw new DongException(RespBeanEnum.START_SECKILL_ERROR);
        if(end < 0)
            throw new DongException(RespBeanEnum.END_SECKILL_ERROR);

        /**
         * 用hashmap标记是否访问redis，此处应记得，当商品秒杀完，不能直接访问redis
         * ,想再进行测试时，直接修改数据库需要重启服务，
         * 最正确应该另外编写修改库存接口（该接口没实现，该项目模拟秒杀过程，当用户在高并发秒杀时，不适合频繁修改数据库）
         * 接口实现，记得更新数据库再更新redis，再将hashmap标记打开
         */
        Boolean flag = goodCountMap.get(goodId);
        if(flag)
            throw new DongException(RespBeanEnum.GOODS_SOLDOUT);

        //判断是否重复购买
        SeckillOrder seckillOrder = orderService.getOrderById(user.getPhone(), goodId);
        if(seckillOrder != null)
            throw new DongException(RespBeanEnum.REPEATE_SECKILL);


        //使用redis预减商品
        Long decrement = redisTemplate.opsForValue().decrement("seckillGoods:" + goodId);
        if(decrement < 0) {
            //标记秒杀结束，不再访问redis，
            goodCountMap.put(goodId,true);
            //秒杀完商品为-1，自增表示redis商品为0
            redisTemplate.opsForValue().increment("seckillGoods:" + goodId);
            throw new DongException(RespBeanEnum.SECKILL_OVER);
        }

        SeckillMessage seckillMessage = new SeckillMessage();
        seckillMessage.setPhone(user.getPhone());
        seckillMessage.setGoodsId(goodId);
        rabbitTemplate.convertAndSend(RabbitMQConfig.SECKILL_ROUTER_EXCHANGE,"",seckillMessage);

    }

    /**
     * 初始化bean的时候会执行此方法
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //openfeign调用good接口获取商品列表
        Map<String, Object> data = goodsService.getGoodsList().data;
        Map map = JSON.parseObject(JSON.toJSONString(data), Map.class);
        List<GoodsVo> goodsVoList = (List<GoodsVo>) data.get("list");
        if(goodsVoList == null)
            return;

        for (int i = 0; i < goodsVoList.size(); i++) {
            /**
             * rpc远程调用在底层使用的HTTPClient，所以在传递参数的时候，需要有个顺序，
             * 当你传递map的时候map里面的值也要有顺序，不然服务层在接的时候就出问题了，
             * 于是map会转为linkedhashMap
             * 使用fastjson转换为对象
             */
            GoodsVo goodsVo = JSON.parseObject(JSON.toJSONString(goodsVoList.get(i)), GoodsVo.class);

            redisTemplate.opsForValue().set("seckillGoods:" + goodsVo.getId(), goodsVo.getStockCount());
            //初始化goodCountMap，商品没有卖光,库存商品大于0表示没有卖光，标记false
            if (goodsVo.getStockCount() > 0)
                goodCountMap.put(goodsVo.getId(),false);
            else
                goodCountMap.put(goodsVo.getId(),true);
        }
    }

    //使用rabbitmq异步修改数据库
    @RabbitListener(queues = RabbitMQConfig.SECKILL_QUEUE)
    public void doSeckillByPhone(SeckillMessage seckillMessage) {

        String phone = seckillMessage.getPhone();
        Long goodsId = seckillMessage.getGoodsId();

        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        Integer stockCount = goodsVo.getStockCount();
        if(stockCount <= 0)
            return;

        SeckillOrder orderById = orderService.getOrderById(phone, goodsId);
        if(orderById != null)
            return;

        //秒杀减库存写入订单
        OrderVo orderVo = new OrderVo();
        orderVo.setPhone(phone);
        orderVo.setGoodsVo(goodsVo);
        orderVo.setGoodsId(goodsVo.getId());
        try{
            orderService.seckill(orderVo);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getSeckillResult(String phone, Long goodId) {
        SeckillOrder order = orderService.getOrderById(phone, goodId);
        if(order != null) {
            return order.getId();
        }else {
            boolean flag = getGoodsOver(goodId);
            if(flag)
                //返回-1，库存结束
                return -1;
            else
                //放回0继续轮询
                return 0;
        }
    }
    //查看库存是否标记结束
    private boolean getGoodsOver(long goodsId) {
        //如果存在key，说明库存已结束
        return redisTemplate.hasKey("isGoodOver:"+goodsId);
    }
}
