package com.seckill;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {
    SUCCESS(20000,"成功"),
    ERROR(20001,"失败"),

    //登录模块
    AUTHRNTICATION_FAILED(28004,"鉴权失败"),
    LOGIN_ERROR(20001,"登录异常，请重试"),
    MOBILE_NOT_EXIST(20002,"手机号码不存在"),
    PASSWORD_ERROR(20003,"账号密码错误"),

    //商品模块
    GOODS_SOLDOUT(20004,"商品已卖光"),

    //秒杀模块
    REPEATE_SECKILL(20005,"请勿重复抢购此商品"),
    SECKILL_OVER(20006,"秒杀已结束"),
    ACCESS_LIMIT_REACHED(20007, "访问高峰期，请稍等！"),
    START_SECKILL_ERROR(20008, "秒杀还未开始!"),
    END_SECKILL_ERROR(20009, "秒杀已结束!")
    ;

    private final Integer code;
    private final String message;
}
