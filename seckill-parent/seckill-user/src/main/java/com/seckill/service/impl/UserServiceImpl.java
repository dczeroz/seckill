package com.seckill.service.impl;

import com.seckill.MD5;
import com.seckill.RespBeanEnum;
import com.seckill.entity.User;
import com.seckill.exceptionhandler.DongException;
import com.seckill.mapper.UserMapper;
import com.seckill.service.LoginService;
import com.seckill.service.UserService;
import com.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    /**注意，此处别习惯性使用@Autowired，@Autowired默认按照类型装配的
     * 当项目不存在redisTemplate时
     *redis 自动导入时，泛型只能有两种
     * 1：RedisTemplate<Object, Object>
     * 2：StringRedisTemplate extends RedisTemplate<String, String>
     * 此处应该按名称注入
     */
    @Resource
    private RedisTemplate<String,User> redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String login(HttpServletResponse response,LoginVo loginVo) {
        if(loginVo == null)
            throw new DongException(RespBeanEnum.LOGIN_ERROR);

        String phone = loginVo.getPhone();
        if(phone == null || phone.length() != 11)
            throw new DongException(RespBeanEnum.LOGIN_ERROR);

        String password = loginVo.getPassword();
        if(password == null)
            throw new DongException(RespBeanEnum.LOGIN_ERROR);

        User user = getByphone(phone);
        if(user == null)
            throw new DongException(RespBeanEnum.MOBILE_NOT_EXIST);

        String dbPassWord = user.getPassword();
        if(!MD5.encrypt(password).equals(dbPassWord))
            throw new DongException(RespBeanEnum.PASSWORD_ERROR);

        //生成oken
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //保存token到redis，获取用户信息，直接查询redis
        addCookie(response,token,user);
        //返回token
        return token;
    }

    private User getByphone(String phone) {
        //查看redis是否有user对象，减少查询数据库次数
        User user = redisTemplate.opsForValue().get("user:" + phone);
        if(user != null)
            return user;

        //查询数据库，并存入redis
        user = userMapper.getByphone(phone);
        if (user != null)
            redisTemplate.opsForValue().set("user:" + phone,user);

        return user;
    }

    /**
     * 将token保存到redis,并存入cookie
     * 不用jwt原因，jwt无法延长token过期时间
     * @param token
     * @param user
     */
    public void addCookie(HttpServletResponse response,String token, User user) {
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
