package com.seckill.service.impl;

import com.seckill.entity.OrderInfo;
import com.seckill.entity.SeckillOrder;
import com.seckill.mapper.OrderMapper;
import com.seckill.service.GoodsService;
import com.seckill.service.OrderService;
import com.seckill.vo.GoodsVo;
import com.seckill.vo.OrderInfoVo;
import com.seckill.vo.OrderVo;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private RedisTemplate<String,SeckillOrder> redisTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private GoodsService goodsService;

    @Override
    public SeckillOrder getOrderById(String phone, Long goodsId) {
        SeckillOrder seckillOrder = redisTemplate.opsForValue().get("order:" + phone + ":" + goodsId);
        return seckillOrder;
    }

    @Override
    @GlobalTransactional
    public void seckill(OrderVo orderVo) {
        boolean seckillFlag = goodsService.reduceStock(orderVo.getGoodsVo());
        if(seckillFlag) {
            createOrder(orderVo);
        }else {
            setGoodsOver(orderVo.getGoodsId());
        }

    }

    public void createOrder(OrderVo orderVo) {
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setPhone(orderVo.getPhone());
        seckillOrder.setGoodsId(orderVo.getGoodsId());
        orderMapper.createOrder(seckillOrder);
        Long orderId = seckillOrder.getId();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(orderId);
        orderInfo.setGoodsName(orderVo.getGoodsVo().getGoodsName());
        orderInfo.setGoodsPrice(orderVo.getGoodsVo().getSeckillPrice());
        orderInfo.setGoodsCount(orderVo.getGoodsVo().getGoodsCount());
        orderInfo.setStatus(0);
        orderInfo.setCreateDate(new Date());
        orderMapper.createOrderInfo(orderInfo);
        redisTemplate.opsForValue().set("order:" + orderVo.getPhone() + ":" + orderVo.getGoodsId(), seckillOrder);
    }

    //标记库存是否结束
    private void setGoodsOver(long goodsId) {
        //上面已有RedisTemplate<String,SeckillOrder>
        RedisTemplate newRedisTemplate = new RedisTemplate();
        newRedisTemplate.opsForValue().set("isGoodOver:"+goodsId,true);
    }

    @Override
    public List<OrderInfoVo> getOrderList(String phone) {
        return orderMapper.getOrderList(phone);
    }
}
