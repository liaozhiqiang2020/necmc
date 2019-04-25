package com.sv.mc.service;

import com.sv.mc.pojo.PermissionEntity;
import com.sv.mc.pojo.RoleEntity;

import java.util.List;
import java.util.Set;

public interface RoleService<T>{
//    void save(sysRoleEntity sysRoleEntity) throws UserRolePermissionDuplicatedBindingException;

   RoleEntity findRoleByRoleName(String roleName);
    /**
     * 分页查询所有role
     * @return 分页集合
     */
    List<RoleEntity> findEntitiesPager();

    /**
     * 不分页查询所有role
     * @return 所有role
     */
    List<RoleEntity> findAllRole();

    /**
     *更新role对象
     * @param role role对象
     * @return 更新的role对象
     */
    RoleEntity updateRole(RoleEntity role);
    /**
     *保存role对象
     * @param role role对象
     * @return 保存的role对象
     */
    RoleEntity saveRole(RoleEntity role);

    /**
     * 逻辑删除一个role对象
     * @param role 需要删除的role对象
     */
    void deleteRole(RoleEntity role);

    /**
     * 根据Id查询role
     * @param roleId role主键
     * @return 查询结果
     */
    RoleEntity findRoleById(int roleId);

 /**
  * 查询当前角色所拥有权限
  * @param roleId 权限主键Id
  * @return 所有权限
  */
 Set<PermissionEntity> findRolePermission(int roleId);

 /**
  * 查询角色未拥有的权限
  * @param roleId 权限主键Id
  * @return 未拥有的权限
  */
 List<PermissionEntity> findRoleUnPermission(int roleId);

 /**
  * 角色删除权限
  * @param roleId 权限主键Id
  * @param pId 权限id
  * @return 删除的权限
  */
 Set<PermissionEntity> roleDeletePermission(int roleId, int pId);

 /**
  * 角色添加权限
  * @param roleId 权限主键Id
  * @param pId 权限id
  * @return 添加的权限
  */
 Set<PermissionEntity> roleAddPermission(int roleId, int pId);

}
