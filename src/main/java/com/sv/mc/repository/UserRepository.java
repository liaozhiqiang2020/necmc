package com.sv.mc.repository;

import com.sv.mc.pojo.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long>, PagingAndSortingRepository<UserEntity, Long> {

    //根据用户账号查询用户
    @Query("from UserEntity as u where u.userName = :userName")
    UserEntity findUserByUserName(@Param("userName") String userName);

    //根据Id查询用户
    @Query("from UserEntity as u where u.id = :uId")
    UserEntity findUserById(@Param("uId") int uId);

    //查询所有状态为正常的用户
    @Query("from UserEntity as u where u.status = 1")
    List<UserEntity> findAllByStatus();

    //查询所有场地公司
//    @Query(nativeQuery = true,value = "SELECT name ,id FROM mc_place,mc_")
}
