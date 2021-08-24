package com.seckill.service;

import com.seckill.entity.SeckillOrder;
import com.seckill.vo.GoodsVo;
import com.seckill.vo.OrderInfoVo;
import com.seckill.vo.OrderVo;

import java.util.List;

public interface OrderService {
    SeckillOrder getOrderById(String phone, Long goodsId);

    void seckill(OrderVo orderVo);

    List<OrderInfoVo> getOrderList(String phone);
}
