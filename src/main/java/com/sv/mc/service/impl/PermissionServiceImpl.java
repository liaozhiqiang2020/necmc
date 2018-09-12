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

    /**
     *根据权限名查询用户
     * @param permissionName
     * @return
     */
    @Override
    @Transactional
    public PermissionEntity findPermissionByPermissionName(String permissionName) {
        return this.permissionRepository.findByPermissionName(permissionName);
    }

    /**
     *
     * @return
     */
    @Override
    @Transactional
    public List<PermissionEntity> findEntitiesPager() {
        return null;
    }

    /**
     * 查询所有权限
     * @return
     */
    @Override
    @Transactional
    public List<PermissionEntity> findAllPermission() {
        return this.permissionRepository.findAll();
    }

    /**
     * 更新权限
     * @param permission
     * @return
     */
    @Override
    @Transactional
    public PermissionEntity updatePermission(PermissionEntity permission) {
        return this.permissionRepository.save(permission);
    }

    /**
     * 新建权限
     * @param permission
     * @return
     */
    @Override
    @Transactional
    public PermissionEntity savePermission(PermissionEntity permission) {
        return this.permissionRepository.save(permission);
    }

    /**
     * 根据Id查询权限
     * @param permissionId
     * @return
     */
    @Override
    @Transactional
    public PermissionEntity findPermissionById(int permissionId) {
        return this.permissionRepository.findByPermissionId(permissionId);
    }
}
