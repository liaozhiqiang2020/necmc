package com.sv.mc.service;

import com.sv.mc.pojo.sysPermissionEntity;

public interface PermissionService<T> {
    void save(sysPermissionEntity sysPermissionEntity);

    sysPermissionEntity findByPermissionName(String name);

    void delet(sysPermissionEntity sysPermissionEntity);

}
