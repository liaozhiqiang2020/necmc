package com.sv.mc.repository;

import com.sv.mc.pojo.PriceEntity;
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

    //查询所有状态为正常的用户
    @Query("from UserEntity as u where u.status = 1 and u.id != :id")
    List<UserEntity> findAllByStatusId(@Param("id") int id);

    /**
     * 分页查询所有可用价格
     * @param page
     * @param pageSize
     * @return
     */
    @Query(value="select * from mc_user as u where u.status = 1 LIMIT :offset,:pageSize",nativeQuery=true)
    List<UserEntity> findAllUserByPage(@Param("offset") Integer page, @Param("pageSize") Integer pageSize);

    /**
     * 查询数量
     * @return
     */
    @Query(value="select count(*) from mc_user as u where u.status= 1",nativeQuery = true)
    int findPriceTotal();
}
