package com.seckill.handle;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.seckill.R;
import com.seckill.RespBeanEnum;
import com.seckill.entity.User;
import com.seckill.exceptionhandler.DongException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Sentinel服务降级熔断返回类
 */
public class CustomerBlockHandler {
    public static R handlerException(User user, Long goodId) {
        throw new DongException(RespBeanEnum.ACCESS_LIMIT_REACHED);
    }


    public static R handlerException1(User user, Long goodId,BlockException exception) {
        throw new DongException(RespBeanEnum.ACCESS_LIMIT_REACHED);
    }

}
