package com.sv.triangle.repository;

import com.sv.triangle.pojo.UserEntity;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long>, PagingAndSortingRepository<UserEntity, Long> {

    @Query("from UserEntity as u where u.userName = :userName")
    UserEntity findUserByUserName(@Param("userName") String userName);
}
