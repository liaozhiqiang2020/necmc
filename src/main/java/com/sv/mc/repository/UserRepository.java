package com.sv.mc.repository;

import com.sv.mc.pojo.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long>, PagingAndSortingRepository<UserEntity, Long> {

    @Query("from UserEntity as u where u.userName = :userName")
    UserEntity findUserByUserName(@Param("userName") String userName);
}
