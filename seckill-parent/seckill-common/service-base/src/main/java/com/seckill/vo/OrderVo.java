package com.seckill.vo;

import com.seckill.entity.SeckillOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo extends SeckillOrder {
    //goodsvo
    private GoodsVo goodsVo;
}
