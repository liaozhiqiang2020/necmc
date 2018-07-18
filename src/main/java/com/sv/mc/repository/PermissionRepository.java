package com.sv.mc.repository;

import com.sv.mc.pojo.sysPermissionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends BaseRepository<sysPermissionEntity, Long> {

    @Query("from sysPermissionEntity as p where p.name = :permission")
    sysPermissionEntity findByPermissionName(@Param("permission") String permissionName);


}
