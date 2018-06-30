package com.sv.triangle.service.impl;

import com.sv.triangle.exception.UserRolePermissionDuplicatedBindingException;
import com.sv.triangle.pojo.PermissionEntity;
import com.sv.triangle.repository.PermissionRepository;
import com.sv.triangle.service.PermissionService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class Permissionlmpl implements PermissionService {

    @Resource
    private PermissionRepository dao;

    @Override
    @Transactional
    @CachePut(value = "user",key = "#root.caches[0].name ")
    public void save(PermissionEntity permissionEntity) {
        dao.save(permissionEntity);
    }

    @Override
    @Transactional
    @Cacheable(value = "user",key = "#root.caches[0].name ")
    public PermissionEntity findByPermissionName(String name) {
        return this.dao.findByPermissionName(name);
    }

    @Override
    @CacheEvict(value = "user",key = "#root.caches[0].name ")
    public void delet(PermissionEntity permissionEntity) {
        dao.delete(permissionEntity);
    }

}