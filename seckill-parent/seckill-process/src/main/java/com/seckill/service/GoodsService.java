package com.seckill.service;

import com.seckill.R;
import com.seckill.vo.GoodsVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient("seckill-goods")
@Component
public interface GoodsService {
    @GetMapping("/seckill/goods/list")
    public R getGoodsList();

    @PostMapping("/seckill/goods/reduceStock")
    public boolean reduceStock(GoodsVo goodsVo);

    @GetMapping("/seckill/goods/getByGoodsId/{id}")
    public GoodsVo getGoodsVoByGoodsId(@PathVariable("id") Long id);
}
