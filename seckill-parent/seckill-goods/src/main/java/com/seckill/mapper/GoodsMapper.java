package com.seckill.mapper;


import com.seckill.entity.SeckillGoods;
import com.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {
    List<GoodsVo> getGoodsList();

    int getVersionByGoodsId(Long id);

    Integer reduceStockByVersion(SeckillGoods skGoods);

    GoodsVo getGoodsVoByGoodId(Long id);
}
