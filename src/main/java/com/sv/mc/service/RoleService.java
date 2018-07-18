package com.sv.mc.service;

import com.sv.mc.pojo.RoleEntity;

import java.util.List;

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



}
