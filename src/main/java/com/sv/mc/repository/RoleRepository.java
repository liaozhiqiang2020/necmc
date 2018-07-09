package com.sv.mc.repository;

import com.sv.mc.pojo.sysRoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<sysRoleEntity, Long>, PagingAndSortingRepository<sysRoleEntity, Long> {
    @Query("from sysRoleEntity as r where r.roleName = :roleName")
    sysRoleEntity findRoleByRoleName(@Param("roleName") String roleName);
}
