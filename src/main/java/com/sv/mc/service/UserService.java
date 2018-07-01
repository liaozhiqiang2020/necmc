package com.sv.mc.service;

import com.sv.mc.pojo.UserEntity;

import java.util.List;

public interface UserService<T> extends BaseService<T> {

    int specificCalculate();

    List<UserEntity> findEntitiesPager();

    UserEntity findUserByUserName(String userName);
}
