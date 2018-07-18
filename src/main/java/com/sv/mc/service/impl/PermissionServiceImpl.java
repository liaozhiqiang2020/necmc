package com.sv.mc.service.impl;

import com.sv.mc.pojo.PermissionEntity;
import com.sv.mc.repository.PermissionRepository;
import com.sv.mc.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService{
    @Resource
    PermissionRepository permissionRepository;
    @Override
    public PermissionEntity findPermissionByPermissionName(String permissionName) {
        return this.permissionRepository.findByPermissionName(permissionName);
    }

    @Override
    public List<PermissionEntity> findEntitiesPager() {
        return null;
    }

    @Override
    public List<PermissionEntity> findAllPermission() {
        return this.permissionRepository.findAll();
    }

    @Override
    public PermissionEntity updatePermission(PermissionEntity permission) {
        return this.permissionRepository.save(permission);
    }

    @Override
    public PermissionEntity savePermission(PermissionEntity permission) {
        return this.permissionRepository.save(permission);
    }

    @Override
    public PermissionEntity findPermissionById(int permissionId) {
        return this.permissionRepository.findByPermissionName(permissionId);
    }
}
