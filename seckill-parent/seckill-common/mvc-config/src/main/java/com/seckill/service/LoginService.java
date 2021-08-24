package com.seckill.service;

import com.seckill.entity.User;

import javax.servlet.http.HttpServletResponse;

public interface LoginService {
    void addCookie(HttpServletResponse response, String token, User user);

    User getByToken(HttpServletResponse response,String token);
}
