package com.seckill.exceptionhandler;

import com.seckill.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DongException extends RuntimeException {

    private Integer code;//状态码

    private String msg;//异常信息

    public DongException(RespBeanEnum respBeanEnum) {
        this.code = respBeanEnum.getCode();
        this.msg = respBeanEnum.getMessage();
    }
}
