package com.seckill.controller;

import com.seckill.R;
import com.seckill.entity.User;
import com.seckill.service.LoginService;
import com.seckill.service.UserService;
import com.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("seckill/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public R login(HttpServletResponse response,@RequestBody LoginVo loginVo) {
        String token = userService.login(response,loginVo);
        return R.ok().data("token",token);
    }
    //获取用户信息
    @GetMapping("/getUserInfo")
    public R getUserInfo(User user) {
        return R.ok().data("user",user);
    }


}
