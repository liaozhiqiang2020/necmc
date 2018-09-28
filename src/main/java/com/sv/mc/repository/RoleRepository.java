package com.sv.mc.repository;

import com.sv.mc.pojo.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 权限dao
 */
@Repository
public interface RoleRepository extends BaseRepository<RoleEntity, Long>, PagingAndSortingRepository<RoleEntity, Long> {

    /**
     * 根据角色名来查询权限
     * @param roleName 角色名
     * @return 权限信息
     */
    @Query("from RoleEntity as r where r.roleName = :roleName")
    RoleEntity findRoleByRoleName(@Param("roleName") String roleName);

    /**
     * 根据Id 来查询权限
     * @param id 主键id
     * @return 权限信息
     */
    @Query("from RoleEntity as r where r.id = :rId")
    RoleEntity findById(@Param("rId") int id);


}
