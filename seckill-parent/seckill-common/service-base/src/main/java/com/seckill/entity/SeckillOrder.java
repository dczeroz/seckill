package com.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillOrder {
    //秒杀订单id
    private Long id;
    //用户手机号码
    private String phone;
    //商品id
    private Long goodsId;
}
