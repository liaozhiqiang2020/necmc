package com.sv.mc.service.impl;

import com.sv.mc.pojo.PermissionEntity;
import com.sv.mc.repository.PermissionRepository;
import com.sv.mc.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService{
    @Resource
    PermissionRepository permissionRepository;
    @Override
    @Transactional
    public PermissionEntity findPermissionByPermissionName(String permissionName) {
        return this.permissionRepository.findByPermissionName(permissionName);
    }

    @Override
    @Transactional
    public List<PermissionEntity> findEntitiesPager() {
        return null;
    }

    @Override
    @Transactional
    public List<PermissionEntity> findAllPermission() {
        return this.permissionRepository.findAll();
    }

    @Override
    @Transactional
    public PermissionEntity updatePermission(PermissionEntity permission) {
        return this.permissionRepository.save(permission);
    }

    @Override
    @Transactional
    public PermissionEntity savePermission(PermissionEntity permission) {
        return this.permissionRepository.save(permission);
    }

    @Override
    @Transactional
    public PermissionEntity findPermissionById(int permissionId) {
        return this.permissionRepository.findByPermissionId(permissionId);
    }
}
