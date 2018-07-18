package com.sv.mc.repository;

import com.sv.mc.pojo.PermissionEntity;
import com.sv.mc.pojo.sysPermissionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends BaseRepository<PermissionEntity, Long> {

    @Query("from PermissionEntity as p where p.permissionsName = :permission")
    PermissionEntity findByPermissionName(@Param("permission") String permissionName);

    @Query("from PermissionEntity as p where p.id = :pId")
    PermissionEntity findByPermissionName(@Param("pId") int pId);

}
