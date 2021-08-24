package com.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    //订单详情表id
    private Long id;
    //订单id
    private Long orderId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer status;
    private Date createDate;
}
