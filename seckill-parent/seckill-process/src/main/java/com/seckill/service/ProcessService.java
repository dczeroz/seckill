package com.seckill.service;

import com.seckill.R;
import com.seckill.entity.User;

public interface ProcessService {
    void doSeckill(User user, Long goodId);

    long getSeckillResult(String phone, Long goodId);
}
