package com.sv.mc.service;

import com.sv.mc.pojo.RoleEntity;
import com.sv.mc.pojo.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 魏帅志
 * @param <T>
 */
public interface UserService<T>  {

    /**
     * 分页查询所有user
     * @return 分页集合
     */
    Page<UserEntity> findEntitiesPager(Pageable pageable);

    /**
     * 不分页查询所有user
     * @return 所有User
     */
    List<UserEntity> findAllUser();

    /**
     * 返回所有状态为正常的用户
     */
    String findAllByStatus();
    /**
     *更新user对象
     * @param user user对象
     * @return 更新的user对象
     */
    UserEntity updateUser(Map<String,Object> map);
    /**
     *保存user对象
     * @param map user对象
     * @return 保存的user对象
     */
    UserEntity saveUser(Map<String,Object> map);

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

    /**
     * 根据用户id查询其所拥有角色
     * @param userId
     * @return
     */
    Set<RoleEntity> findUserRole(int userId);

    /**
     * 删除用户所拥有的角色
     * @param listMap
     * @return
     */
    Set<RoleEntity> deleteUserRole(Map<String,Object> listMap);


    /**
     *用户添加角色
     * @param userId
     * @param roleId
     * @return
     */
    Set<RoleEntity> addUserRole(int userId,int roleId);

    /**
     * 查询用户未绑定的角色
     * @param userId
     * @return
     */
    List<RoleEntity> userUnRole(int userId);

    List<Object> findAllplace();
}
