package com.sv.mc.service.impl;

import com.sv.mc.pojo.PermissionEntity;
import com.sv.mc.pojo.RoleEntity;
import com.sv.mc.repository.PermissionRepository;
import com.sv.mc.repository.RoleRepository;
import com.sv.mc.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    RoleRepository roleRepository;

    @Resource
    PermissionRepository permissionRepository;

    @Override
    @Transactional
    public RoleEntity findRoleByRoleName(String roleName) {
        return this.roleRepository.findRoleByRoleName(roleName);
    }

    @Override
    @Transactional
    public List<RoleEntity> findEntitiesPager() {
        return null;
    }

    @Override
    @Transactional
    public List<RoleEntity> findAllRole() {
        return this.roleRepository.findAll();
    }


    @Override
    @Transactional
    public RoleEntity updateRole(RoleEntity role) {
        return this.roleRepository.save(role);
    }

    @Override
    @Transactional
    public RoleEntity saveRole(RoleEntity role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void deleteRole(RoleEntity role) {
        this.roleRepository.delete(role);
    }

    @Override
    @Transactional
    public RoleEntity findRoleById(int roleId) {
        return this.roleRepository.findById(roleId);
    }

    @Override
    public Set<PermissionEntity> findRolePermission(int roleId) {
        RoleEntity role = this.roleRepository.findById(roleId);
        return role.getPermissionEntityHashSet();
    }

    @Override
    public List<PermissionEntity> findRoleUnPermission(int roleId) {
        List<PermissionEntity> permissionList = this.permissionRepository.findAll();
        RoleEntity role = this.roleRepository.findById(roleId);
        permissionList.removeAll(role.getPermissionEntityHashSet());
        return permissionList;
    }

    @Override
    public Set<PermissionEntity> roleDeletePermission(int roleId, int pId) {
        RoleEntity role = this.roleRepository.findById(roleId);
        PermissionEntity permission = this.permissionRepository.findByPermissionId(pId);

        Set<PermissionEntity> permissionEntities = role.getPermissionEntityHashSet();
        String des = "";
        for (PermissionEntity p : permissionEntities
                ) {
            des = des + p.getPermissionsName() + " ";
        }
        des = des + permission.getPermissionsName();
        role.setDescription(des);
        role.getPermissionEntityHashSet().remove(permission);

        this.roleRepository.save(role);
        return role.getPermissionEntityHashSet();
    }

    @Override
    public Set<PermissionEntity> roleAddPermission(int roleId, int pId) {
        RoleEntity role = this.roleRepository.findById(roleId);
        PermissionEntity permission = this.permissionRepository.findByPermissionId(pId);
        Set<PermissionEntity> permissionEntities = role.getPermissionEntityHashSet();
        String des = "";
        for (PermissionEntity p : permissionEntities
                ) {
            des = des + p.getPermissionsName() + " ";
        }
        des = des + permission.getPermissionsName();
        role.setDescription(des);
        role.getPermissionEntityHashSet().add(permission);
        this.roleRepository.save(role);
        return role.getPermissionEntityHashSet();
    }
}
