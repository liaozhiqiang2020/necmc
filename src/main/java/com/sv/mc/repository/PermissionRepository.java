package com.sv.mc.repository;

import com.sv.mc.pojo.PermissionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限表
 */
@Repository
public interface PermissionRepository extends BaseRepository<PermissionEntity, Long> {

    /**
     * 根据权限名称查询权限
     * @param permissionName 权限名称
     * @return 权限信息
     */
    @Query("from PermissionEntity as p where p.permissionsName = :permission")
    PermissionEntity findByPermissionName(@Param("permission") String permissionName);

    /**
     * 根据权限Id 查询权限信息
     * @param pId 主键Id
     * @return 权限信息
     */
    @Query("from PermissionEntity as p where p.id = :pId")
    PermissionEntity findByPermissionId(@Param("pId") int pId);

    /**
     *  根据用户Id 查询权限信息
     * @param userId 用户Id
     * @return 权限信息集合
     */

    @Query("select p from PermissionEntity as p,UserEntity as u,RoleEntity as r where u.id = :uId")
    List<PermissionEntity> findPermissionByUser(@Param("uId") int userId);

}
