package com.sv.triangle.repository;

import com.sv.triangle.pojo.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends BaseRepository<RoleEntity, Long>, PagingAndSortingRepository<RoleEntity, Long> {
    @Query("from RoleEntity as r where r.roleName = :roleName")
    RoleEntity findRoleByRoleName(@Param("roleName") String roleName);
}
