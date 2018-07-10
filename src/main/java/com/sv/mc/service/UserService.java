package com.sv.mc.service;

import com.sv.mc.pojo.sysUserEntity;

import java.util.List;

public interface UserService<T> extends BaseService<T> {

    int specificCalculate();

    List<sysUserEntity> findEntitiesPager();

    sysUserEntity findUserByUserName(String userName);
}
