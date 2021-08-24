package com.seckill.service.impl;

import com.seckill.entity.User;
import com.seckill.service.LoginService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    RedisTemplate<String,User> redisTemplate;
    /**
     * 将token保存到redis,并存入cookie
     * 不用jwt原因，jwt无法延长token过期时间
     * @param token
     * @param user
     */
    public void addCookie(HttpServletResponse response, String token, User user) {
        //token保存一小时，前端cookie也保存一小时
        redisTemplate.opsForValue().set("user:" + token,user,1, TimeUnit.HOURS);
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(1 * 60 * 60);
        cookie.setPath("/");//设置为网站根目录
        response.addCookie(cookie);
    }

    /**
     * 根据token获取user对象
     * @param token
     * @return
     */
    public User getByToken(HttpServletResponse response,String token) {
        if(StringUtils.isEmpty(token))
            return null;

        User user = redisTemplate.opsForValue().get("user:" + token);

        //延长登录，一天未登录需要重新登录
        addCookie(response,token,user);
        return user;
    }
}
