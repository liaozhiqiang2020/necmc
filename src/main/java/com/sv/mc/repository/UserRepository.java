package com.sv.mc.repository;

import com.sv.mc.pojo.sysUserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<sysUserEntity, Long>, PagingAndSortingRepository<sysUserEntity, Long> {

    @Query("from sysUserEntity as u where u.userName = :userName")
    sysUserEntity findUserByUserName(@Param("userName") String userName);
}
