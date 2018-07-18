package com.sv.mc.service.impl;

import com.sv.mc.pojo.RoleEntity;
import com.sv.mc.repository.RoleRepository;
import com.sv.mc.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleRepository roleRepository;

    @Override
    public RoleEntity findRoleByRoleName(String roleName) {
        return this.roleRepository.findRoleByRoleName(roleName);
    }

    @Override
    public List<RoleEntity> findEntitiesPager() {
        return null;
    }

    @Override
    public List<RoleEntity> findAllRole() {
        return this.roleRepository.findAll();
    }


    @Override
    public RoleEntity updateRole(RoleEntity role) {
        return this.roleRepository.save(role);
    }

    @Override
    public RoleEntity saveRole(RoleEntity role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(RoleEntity role) {
        this.roleRepository.delete(role);
    }

    @Override
    public RoleEntity findRoleById(int roleId) {
        return this.roleRepository.findById(roleId);
    }
}
