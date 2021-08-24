package com.seckill.mapper;

import com.seckill.entity.OrderInfo;
import com.seckill.entity.SeckillOrder;
import com.seckill.vo.GoodsVo;
import com.seckill.vo.OrderInfoVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    Long createOrder(SeckillOrder seckillOrder);

    void createOrderInfo(OrderInfo orderInfo);

    List<OrderInfoVo> getOrderList(String phone);
}
