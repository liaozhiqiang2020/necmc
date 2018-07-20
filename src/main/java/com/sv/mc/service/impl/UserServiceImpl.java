package com.sv.mc.service.impl;

import com.sv.mc.pojo.UserEntity;
import com.sv.mc.repository.UserRepository;
import com.sv.mc.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService<UserEntity> {

    @Resource
    UserRepository userRepository;

    @Override
    @Transactional
    public List<UserEntity> findEntitiesPager() {
        return null;
    }

    @Override
    @Transactional
    public List<UserEntity> findAllUser() {
       return this.userRepository.findAll();
    }

    @Override
    @Transactional
    public List<UserEntity> findAllByStatus() {
        return this.userRepository.findAllByStatus();
    }

    @Override
    @Transactional
    public UserEntity updateUser(UserEntity user) {
        user.setAuthenticationString( DigestUtils.md5DigestAsHex(user.getAuthenticationString().getBytes()));
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public UserEntity saveUser(UserEntity user) {
        user.setStatus(1);
        user.setAuthenticationString( DigestUtils.md5DigestAsHex(user.getAuthenticationString().getBytes()));
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(UserEntity user) {
       user.setStatus(0);
       this.userRepository.save(user);
    }

    @Override
    @Transactional
    public UserEntity findUserById(int userId) {
       return this.userRepository.findUserById(userId);
    }
}