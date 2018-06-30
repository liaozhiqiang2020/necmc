package com.sv.triangle.service.impl;

import com.sv.triangle.pojo.UserEntity;
import com.sv.triangle.repository.UserRepository;
import com.sv.triangle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService<UserEntity> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public int specificCalculate() {
        return 1 + 1;
    }

    @Override
    @Transactional
    @Cacheable
    public List<UserEntity> findAllEntities() {
        return this.userRepository.findAll();
    }

    @Override
    @Transactional
    public List<UserEntity> findEntitiesPager(){
        PageRequest pageRequest = new PageRequest(0,5);
        Page<UserEntity> userEntityPage = userRepository.findAll(pageRequest);
        return userEntityPage.getContent();
    }

    @Override
    @Transactional
    public UserEntity findUserByUserName(String userName) {
        return this.userRepository.findUserByUserName(userName);
    }
}