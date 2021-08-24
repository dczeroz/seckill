package com.seckill.controller;

import com.seckill.R;
import com.seckill.RespBeanEnum;
import com.seckill.entity.SeckillOrder;
import com.seckill.entity.User;
import com.seckill.exceptionhandler.DongException;
import com.seckill.mapper.OrderMapper;
import com.seckill.service.OrderService;
import com.seckill.vo.GoodsVo;
import com.seckill.vo.OrderInfoVo;
import com.seckill.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seckill/order")
@RefreshScope //支持Nacos的动态刷新功能。
public class OrderController {

    @Autowired
    OrderService orderService;

    //判断重复秒杀
    @GetMapping("/getOrderById/{phone}/{goodsId}")
    public SeckillOrder getOrderById(@PathVariable("phone") String phone,
                                     @PathVariable("goodsId") Long goodsId) {
        return orderService.getOrderById(phone,goodsId);
    }

    //减库存,加订单
    @PostMapping("/seckill")
    public void seckill(@RequestBody OrderVo orderVo) {
        orderService.seckill(orderVo);
    }

    @GetMapping("/orderInfo/list")
    public R getOrderInfoList(User user) {
        if (user == null)
            throw new DongException(RespBeanEnum.AUTHRNTICATION_FAILED);
        List<OrderInfoVo> orderInfoVoList = orderService.getOrderList(user.getPhone());

        return R.ok().data("orderList",orderInfoVoList);
    }
}
