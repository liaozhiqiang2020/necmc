package com.sv.mc.service.impl;

import com.sv.mc.pojo.UserEntity;
import com.sv.mc.pojo.sysUserEntity;
import com.sv.mc.repository.UserRepository;
import com.sv.mc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public UserEntity saveOrUpdateUser(UserEntity user) {
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