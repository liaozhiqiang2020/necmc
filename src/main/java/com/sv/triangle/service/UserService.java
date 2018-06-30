package com.sv.triangle.service;

import com.sv.triangle.pojo.UserEntity;

import java.util.List;

public interface UserService<T> extends BaseService<T> {

    int specificCalculate();

    List<UserEntity> findEntitiesPager();

    UserEntity findUserByUserName(String userName);
}
