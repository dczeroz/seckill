package com.seckill.vo;

import com.seckill.entity.SeckillOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoVo extends SeckillOrder {
    private Long id;
    //订单id
    private Long orderId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;

    private Integer status;
    private Date createDate;
}
