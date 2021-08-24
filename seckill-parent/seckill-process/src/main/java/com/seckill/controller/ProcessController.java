package com.seckill.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.seckill.R;
import com.seckill.entity.User;
import com.seckill.exceptionhandler.DongException;
import com.seckill.handle.CustomerBlockHandler;
import com.seckill.service.OrderService;
import com.seckill.service.ProcessService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckill/process")
@RefreshScope //支持Nacos的动态刷新功能。
public class ProcessController {
    @Autowired
    private  ProcessService processService;

    @GetMapping("/doSeckill/{goodId}")
    //服务限流降级 （限流qps为100，每秒异常总数占通过量的比值超过阈值0.2，个人设置，按照自己需求修改）
    @SentinelResource(value = "seckill",
            blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "handlerException1")
    public R doSeckill(User user, @PathVariable("goodId") Long goodId) {
        processService.doSeckill(user, goodId);
        //放回0表示处于排队中 -1表示失败 返回订单id表示成功
        return R.ok().data("seckillCode",0);
    }

    @GetMapping("/result/{goodId}")
    public R getSeckillResult(User user,@PathVariable("goodId") Long goodId) {
        long seckillResult = processService.getSeckillResult(user.getPhone(), goodId);
        return R.ok().data("flag",seckillResult);
    }
}
