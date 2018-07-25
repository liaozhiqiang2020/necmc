package com.sv.mc.repository;

import com.sv.mc.pojo.PermissionEntity;
import com.sv.mc.pojo.sysPermissionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends BaseRepository<PermissionEntity, Long> {

    @Query("from PermissionEntity as p where p.permissionsName = :permission")
    PermissionEntity findByPermissionName(@Param("permission") String permissionName);

    @Query("from PermissionEntity as p where p.id = :pId")
    PermissionEntity findByPermissionId(@Param("pId") int pId);

    @Query("select p from PermissionEntity as p,UserEntity as u,RoleEntity as r where u.id = :uId")
    List<PermissionEntity> findPermissionByUser(@Param("uId") int userId);

}
