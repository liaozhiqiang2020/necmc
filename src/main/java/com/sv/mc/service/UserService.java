package com.sv.mc.service;

import com.sv.mc.pojo.UserEntity;
import com.sv.mc.pojo.sysUserEntity;

import java.util.List;

/**
 * @author 魏帅志
 * @param <T>
 */
public interface UserService<T>  {

    /**
     * 分页查询所有user
     * @return 分页集合
     */
    List<UserEntity> findEntitiesPager();

    /**
     * 不分页查询所有user
     * @return 所有User
     */
    List<UserEntity> findAllUser();

    /**
     * 返回所有状态为正常的用户
     */
    List<UserEntity> findAllByStatus();
    /**
     *更新或保存user对象
     * @param user user对象
     * @return 更新的user对象
     */
    UserEntity saveOrUpdateUser(UserEntity user);

    /**
     * 逻辑删除一个user对象
     * @param user 需要删除的User对象
     */
    void deleteUser(UserEntity user);

    /**
     * 根据Id查询User
     * @param userId user主键
     * @return 查询结果
     */
    UserEntity findUserById(int userId);
}
