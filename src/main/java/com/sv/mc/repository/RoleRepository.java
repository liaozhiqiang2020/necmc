package com.sv.mc.repository;

import com.sv.mc.pojo.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<RoleEntity, Long>, PagingAndSortingRepository<RoleEntity, Long> {
    //根据角色名查询
    @Query("from RoleEntity as r where r.roleName = :roleName")
    RoleEntity findRoleByRoleName(@Param("roleName") String roleName);

    @Query("from RoleEntity as r where r.id = :rId")
    RoleEntity findById(@Param("rId") int id);


}
