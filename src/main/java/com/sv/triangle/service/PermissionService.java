package com.sv.triangle.service;

import com.sv.triangle.exception.UserRolePermissionDuplicatedBindingException;
import com.sv.triangle.pojo.PermissionEntity;
import com.sv.triangle.pojo.RoleEntity;
import com.sv.triangle.repository.PermissionRepository;

import javax.annotation.Resource;
import java.util.List;

public interface PermissionService<T> {
    void save(PermissionEntity permissionEntity);

    PermissionEntity findByPermissionName(String name);

    void delet(PermissionEntity permissionEntity);

}
