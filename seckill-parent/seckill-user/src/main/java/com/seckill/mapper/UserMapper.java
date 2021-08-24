package com.seckill.mapper;

import com.seckill.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User getByphone(String phone);
}
