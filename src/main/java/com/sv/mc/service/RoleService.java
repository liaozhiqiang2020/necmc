package com.sv.mc.service;

import com.sv.mc.exception.UserRolePermissionDuplicatedBindingException;
import com.sv.mc.pojo.RoleEntity;

public interface RoleService<T> extends BaseService<T> {
    void save(RoleEntity roleEntity) throws UserRolePermissionDuplicatedBindingException;

    RoleEntity findRoleByRoleName(String roleName);

    void removeUserFromRole(String roleName, String userName);
}
