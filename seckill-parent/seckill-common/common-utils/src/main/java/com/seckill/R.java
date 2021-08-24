package com.seckill;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//统一返回结果类
@Data
public class R {
    @ApiModelProperty(value = "是否成功")
    public Boolean success;

    @ApiModelProperty(value = "返回码")
    public Integer code;

    @ApiModelProperty(value = "返回消息")
    public String message;

    @ApiModelProperty(value = "返回数据")
    public Map<String, Object> data = new HashMap<String, Object>();

    private R(){}
//    链式编程

//    成功静态方法
    public static R ok() {
        R r =new R();
        r.setSuccess(true);
        r.setCode(RespBeanEnum.SUCCESS.getCode());
        r.setMessage(RespBeanEnum.SUCCESS.getMessage());
        return r;
    }

    //    失败静态方法
    public static R error() {
        R r =new R();
        r.setSuccess(false);
        r.setCode(RespBeanEnum.ERROR.getCode());
        r.setMessage(RespBeanEnum.ERROR.getMessage());
        return r;
    }
  	public R success(Boolean success){
       	this.setSuccess(success);
        return this;
    }

    public R message(String message){
       	this.setMessage(message);
       	return this;
    }

    public R code(Integer code){
       	this.setCode(code);
       	return this;
    }

    public R data(String key, Object value){
       	this.data.put(key, value);
      	return this;
    }

    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

}
