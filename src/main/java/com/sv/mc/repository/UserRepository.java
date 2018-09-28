package com.sv.mc.repository;

import com.sv.mc.pojo.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户表dao
 */
@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long>, PagingAndSortingRepository<UserEntity, Long> {

    /**
     * 根据 用户名 查询账户是否存在
     * @param userName 用户名
     * @return 用户数量
     */
    @Query(value = "select count(u.user_name) from mc_user u where u.user_name = :userName" , nativeQuery = true)
    int findUserName(@Param("userName") String userName);

    /**
     *  根据用户名查询账户
     * @param userName 用户名
     * @return 用户信息
     */
    @Query("from UserEntity as u where u.userName = :userName")
    UserEntity findUserByUserName(@Param("userName") String userName);



    /**
     * 根据Id 查询用户
     * @param uId 用户id
     * @return 用户信息
     */
    @Query("from UserEntity as u where u.id = :uId")
    UserEntity findUserById(@Param("uId") int uId);



    /**
     * 查询所有状态正常的用户
     * @return 用户信息集合
     */
    @Query("from UserEntity as u where u.status = 1")
    List<UserEntity> findAllByStatus();

    /**
     * 查询Id 以外的账户
     * @param id 用户id
     * @return 用户id集合
     */
    @Query("from UserEntity as u where u.status = 1 and u.id != :id")
    List<UserEntity> findAllByStatusId(@Param("id") int id);

    /**
     * 分页查询所有可用价格
     *
     * @param page 起始个数
     * @param pageSize 截至个数
     * @return 用户集合
     */
    @Query(value = "select * from mc_user as u where u.status = 1 LIMIT :offset,:pageSize", nativeQuery = true)
    List<UserEntity> findAllUserByPage(@Param("offset") Integer page, @Param("pageSize") Integer pageSize);

    /**
     * 查询数量
     * @return 用户数量
     */
    @Query(value = "select count(*) from mc_user as u where u.status= 1", nativeQuery = true)
    int findPriceTotal();
}
