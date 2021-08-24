package com.seckill.service;

import com.seckill.vo.GoodsVo;

import java.util.List;

public interface GoodService {
    List<GoodsVo> getGoodsList();

    void RefreshRedis();

    boolean reduceStock(GoodsVo goodsVo);

    GoodsVo getGoodsVoByGoodId(Long id);
}
