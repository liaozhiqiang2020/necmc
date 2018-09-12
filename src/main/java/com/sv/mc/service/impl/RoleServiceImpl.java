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

    /**
     * 根据角色名查询角色
     * @param roleName
     * @return
     */
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

    /**
     * 查询所有角色
     * @return
     */
    @Override
    @Transactional
    public List<RoleEntity> findAllRole() {
        return this.roleRepository.findAll();
    }


    /**
     * 更新角色
     * @param role role对象
     * @return
     */
    @Override
    @Transactional
    public RoleEntity updateRole(RoleEntity role) {
        return this.roleRepository.save(role);
    }

    /**
     * 新建角色
     * @param role role对象
     * @return
     */
    @Override
    @Transactional
    public RoleEntity saveRole(RoleEntity role) {
        return roleRepository.save(role);
    }

    /**
     * 删除角色
     * @param role 需要删除的role对象
     */
    @Override
    @Transactional
    public void deleteRole(RoleEntity role) {
        this.roleRepository.delete(role);
    }

    /**
     * 根据Id查询角色
     * @param roleId role主键
     * @return
     */
    @Override
    @Transactional
    public RoleEntity findRoleById(int roleId) {
        return this.roleRepository.findById(roleId);
    }

    /**
     * 查询角色绑定的权限
     * @param roleId
     * @return
     */
    @Override
    public Set<PermissionEntity> findRolePermission(int roleId) {
        RoleEntity role = this.roleRepository.findById(roleId);
        return role.getPermissionEntityHashSet();
    }

    /**
     * 查询角色未绑定的权限
     * @param roleId
     * @return
     */
    @Override
    public List<PermissionEntity> findRoleUnPermission(int roleId) {
        List<PermissionEntity> permissionList = this.permissionRepository.findAll();
        RoleEntity role = this.roleRepository.findById(roleId);
        permissionList.removeAll(role.getPermissionEntityHashSet());
        return permissionList;
    }

    /**
     * 角色解绑权限
     * @param roleId
     * @param pId 权限id
     * @return
     */
    @Override
    public Set<PermissionEntity> roleDeletePermission(int roleId, int pId) {
        RoleEntity role = this.roleRepository.findById(roleId);
        PermissionEntity permission = this.permissionRepository.findByPermissionId(pId);

        Set<PermissionEntity> permissionEntities = role.getPermissionEntityHashSet();
        role.getPermissionEntityHashSet().remove(permission);
        String des = "";
        for (PermissionEntity p : permissionEntities
                ) {
            des = des + p.getPermissionsName() + " ";
        }
        role.setDescription(des);


        this.roleRepository.save(role);
        return role.getPermissionEntityHashSet();
    }

    /**
     * 角色添加权限
     * @param roleId
     * @param pId 权限id
     * @return
     */
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
