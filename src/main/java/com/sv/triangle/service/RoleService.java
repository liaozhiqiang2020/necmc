package com.sv.triangle.service;

import com.sv.triangle.exception.UserRolePermissionDuplicatedBindingException;
import com.sv.triangle.pojo.RoleEntity;
import com.sv.triangle.pojo.UserEntity;

public interface RoleService<T> extends BaseService<T> {
    void save(RoleEntity roleEntity) throws UserRolePermissionDuplicatedBindingException;

    RoleEntity findRoleByRoleName(String roleName);

    void removeUserFromRole(String roleName, String userName);
}
