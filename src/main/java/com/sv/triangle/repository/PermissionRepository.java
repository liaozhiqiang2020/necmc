package com.sv.triangle.repository;

import com.sv.triangle.pojo.PermissionEntity;
import com.sv.triangle.pojo.RoleEntity;
import com.sv.triangle.pojo.UserEntity;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends BaseRepository<PermissionEntity, Long>{

    @Query("from PermissionEntity as p where p.name = :permission")
    PermissionEntity findByPermissionName(@Param("permission") String permissionName);
}
