package com.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillGoods {
    //秒杀商品表id
    private Long id;
    //商品id
    private Long goodsId;
    //秒杀商品价格
    private BigDecimal seckillPrice;
    //商品数量
    private Integer goodsCount;
    //剩余库存
    private Integer stockCount;
    //开始时间
    private Date startDate;
    //结束时间
    private Date endDate;
    //版本号，乐观锁
    private int version;

}
