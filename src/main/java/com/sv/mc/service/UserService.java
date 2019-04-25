package com.sv.mc.service;

import com.sv.mc.controller.UserController;
import com.sv.mc.pojo.RoleEntity;
import com.sv.mc.pojo.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
     * @param page  起始个数
     * @param pageSize  截至个数
     * @return 分页集合
     */
    String findEntitiesPager(int page, int pageSize);

    /**
     * 不分页查询所有user
     * @return 所有User
     */
    List<UserEntity> findAllUser();

    /**
     * 返回所有状态为正常的用户
     */
    String findAllByStatus(HttpServletRequest request);
    /**
     *更新user对象
     * @param map user对象
     * @return 更新的user对象
     */
    int updateUser(Map<String, Object> map);
    /**
     *保存user对象
     * @param map user对象
     * @return 保存的user对象
     */
    int saveUser(Map<String, Object> map);

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
     * @return 角色集合
     */
    Set<RoleEntity> deleteUserRole(Map<String, Object> listMap);


    /**
     *用户添加角色
     * @param userId 用户id
     * @param roleId 权限id
     * @return
     */
    Set<RoleEntity> addUserRole(int userId, int roleId);

    /**
     * 查询用户未绑定的角色
     * @param userId 用户id
     * @return 未绑定的角色
     */
    List<RoleEntity> userUnRole(int userId);

    /**
     * 查询所有场地
     * @return 场地集合
     */
    List<Object> findAllplace();

    /**
     * 根据用户所在公司分类查询公司名称
     * @param gradeId 公司分类
     * @param pId 公司分类
     * @return 公司名称
     */
    String findCompanyNameByGradeType(int gradeId, int pId);

    /**
     * 修改密码
     * @param request 请求
     * @param session  用户
     * @param response  响应
     * @return 修改后的密码
     */
    String changePwd(HttpServletRequest request, HttpSession session, HttpServletResponse response);

    /**
     * 保存用户
     * @param map 用户信息
     * @param userController
     * @return 保存的用户信息条数
     */
    @PostMapping("/user/save")
    default int saveUser(@RequestBody Map<String, Object> map, UserController userController) {
        return saveUser(map);
    }
}
