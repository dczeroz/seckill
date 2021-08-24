package com.seckill.service;

import com.seckill.entity.User;
import com.seckill.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    String login(HttpServletResponse response,LoginVo loginVo);

}
