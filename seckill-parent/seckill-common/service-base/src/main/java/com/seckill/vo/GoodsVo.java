package com.seckill.vo;

import com.seckill.entity.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 秒杀商品信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVo extends Goods {
    //秒杀价格
    private Double seckillPrice;
    //秒杀商品总数量
    private Integer goodsCount;
    //剩余库存
    private Integer stockCount;
    //秒杀开始时间
    private Date startDate;
    //秒杀结束时间
    private Date endDate;
    //并发版本控制
    private Integer version;
}
