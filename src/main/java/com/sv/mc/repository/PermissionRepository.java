package com.sv.mc.repository;

import com.sv.mc.pojo.PermissionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends BaseRepository<PermissionEntity, Long>{

    @Query("from PermissionEntity as p where p.name = :permission")
    PermissionEntity findByPermissionName(@Param("permission") String permissionName);
}
