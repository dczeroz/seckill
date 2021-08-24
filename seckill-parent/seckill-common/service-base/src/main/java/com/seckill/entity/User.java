package com.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
//    用户昵称
    private String nickname;
    //用户密码
    private String password;
    //用户号码
    private String phone;
    //用户头像
    private String head;
    //注册日期
    private Date registerDate;
}
