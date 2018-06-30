package com.sv.triangle.service;

import com.google.gson.Gson;
import com.sv.triangle.pojo.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public void add(String key, String user) {


        stringRedisTemplate.opsForValue().set(key,user);
    }


    public void delete(String key) {
        stringRedisTemplate.opsForValue().getOperations().delete(key);
    }
}
