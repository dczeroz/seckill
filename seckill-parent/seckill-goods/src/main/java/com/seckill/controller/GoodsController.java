package com.seckill.controller;

import com.seckill.R;
import com.seckill.service.GoodService;
import com.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seckill/goods")
@RefreshScope //支持Nacos的动态刷新功能。
public class GoodsController {
    @Autowired
    GoodService goodService;

    // 获取商品列表，没隔一段时间，前端获取数据，必须查询redis，不能查数据库
    @GetMapping("/list")
    public R getGoodsList() {
        List<GoodsVo> goodsList = goodService.getGoodsList();
        return R.ok().data("list",goodsList);
    }

    //openfeign调用
    @PostMapping("/reduceStock")
    public boolean reduceStock(@RequestBody GoodsVo goodsVo) {
        return goodService.reduceStock(goodsVo);
    }

    //openfeign调用
    @GetMapping("/getByGoodsId/{id}")
    public GoodsVo getGoodsVoByGoodsId(@PathVariable("id") Long id) {
        return goodService.getGoodsVoByGoodId(id);
    }
}
