//package com.sv.mc.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public class RedisService {
//
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//
//    public void add(String key, String user) {
//
//
//        stringRedisTemplate.opsForValue().set(key,user);
//    }
//
//
//    public void delete(String key) {
//        stringRedisTemplate.opsForValue().getOperations().delete(key);
//    }
//}
