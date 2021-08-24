package com.seckill.service;

import com.seckill.R;
import com.seckill.entity.SeckillOrder;
import com.seckill.vo.OrderVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("seckill-order")
@Component
public interface OrderService {
    @GetMapping("/seckill/order/getOrderById/{phone}/{goodsId}")
    public SeckillOrder getOrderById(@PathVariable("phone") String phone,
                                     @PathVariable("goodsId") Long goodsId);

    //创建订单
    @PostMapping("/seckill/order/seckill")
    public void seckill(OrderVo orderVo);
}
