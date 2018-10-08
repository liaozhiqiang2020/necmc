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
     * 根据权限名查询用户
     * @param permissionName 权限名
     * @return 用户信息
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
     * @return 权限信息
     */
    @Override
    @Transactional
    public List<PermissionEntity> findAllPermission() {
        return this.permissionRepository.findAll();
    }

    /**
     * 更新权限
     * @param permission 修改的权限
     * @return 修改的权限
     */
    @Override
    @Transactional
    public PermissionEntity updatePermission(PermissionEntity permission) {
        return this.permissionRepository.save(permission);
    }

    /**
     * 新建权限
     * @param permission 新增的权限内容
     * @return 修改了的权限内容
     */
    @Override
    @Transactional
    public PermissionEntity savePermission(PermissionEntity permission) {
        return this.permissionRepository.save(permission);
    }

    /**
     * 根据Id查询权限
     * @param permissionId 权限id
     * @return 查询的权限
     */
    @Override
    @Transactional
    public PermissionEntity findPermissionById(int permissionId) {
        return this.permissionRepository.findByPermissionId(permissionId);
    }
}
