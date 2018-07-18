package com.sv.mc.service.impl;

import com.sv.mc.pojo.UserEntity;
import com.sv.mc.repository.UserRepository;
import com.sv.mc.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService<UserEntity> {

    @Resource
    UserRepository userRepository;

    @Override
    public List<UserEntity> findEntitiesPager() {
        return null;
    }

    @Override
    public List<UserEntity> findAllUser() {
       return this.userRepository.findAll();
    }

    @Override
    public List<UserEntity> findAllByStatus() {
        return this.userRepository.findAllByStatus();
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        return this.userRepository.save(user);
    }

    @Override
    public UserEntity saveUser(UserEntity user) {
        user.setStatus(1);
        String password = "daeyulife";
        user.setAuthenticationString( DigestUtils.md5DigestAsHex(password.getBytes()));
        return this.userRepository.save(user);
    }

    @Override
    public void deleteUser(UserEntity user) {
       user.setStatus(1);
       this.userRepository.save(user);
    }

    @Override
    public UserEntity findUserById(int userId) {
       return this.userRepository.findUserById(userId);
    }
}