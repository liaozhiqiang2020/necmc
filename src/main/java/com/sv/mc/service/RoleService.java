package com.sv.mc.service;

import com.sv.mc.exception.UserRolePermissionDuplicatedBindingException;
import com.sv.mc.pojo.sysRoleEntity;

public interface RoleService<T> extends BaseService<T> {
    void save(sysRoleEntity sysRoleEntity) throws UserRolePermissionDuplicatedBindingException;

    sysRoleEntity findRoleByRoleName(String roleName);

    void removeUserFromRole(String roleName, String userName);
}
