package com.sv.mc.controller;

import com.sv.mc.pojo.RoleEntity;
import com.sv.mc.pojo.UserEntity;
import com.sv.mc.service.UserService;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class UserController {

    @Resource
    UserService userService;

    @GetMapping("/user/allPage")
    public String findAllUserPage(@Param("page") String page, @Param("pageSize") String pageSize) {
        return this.userService.findEntitiesPager(Integer.parseInt(page), Integer.parseInt(pageSize));
    }

    /**
     * 查询所有user
     *
     * @return 返回User集合
     */
    @GetMapping("/user/all")
    public List<UserEntity> findUserAll() {

        return this.userService.findAllUser();
    }

    /**
     * 查询所有user
     *
     * @return 返回User集合
     */
    @GetMapping("/user/allStatus")
    public String findAllByStatus(HttpServletRequest request) {
        return this.userService.findAllByStatus(request);
    }


    @GetMapping("/user/allplace1")
    public List<Object> findAll1() {
        return this.userService.findAllplace();
    }

    ;

    /**
     * 根据id查询当前用户
     *
     * @param userId 当前用户id
     * @return 用户信息
     */
    @GetMapping("/user/currentUser")
    public UserEntity findUserById(@RequestParam("userId") int userId) {
        return this.userService.findUserById(userId);
    }

    /**
     * 逻辑删除用户
     *
     * @param userEntity 用户对象
     */
    @PostMapping("/user/delete")
    public void deleteUser(@RequestBody UserEntity userEntity) {
        this.userService.deleteUser(userEntity);
    }

    /**
     * 更新用户
     *
     * @param map
     * @return
     */
    @PostMapping("/user/update")
    public int updateUser(@RequestBody Map<String, Object> map) {
        return this.userService.updateUser(map);
    }

    /**
     * 保存用户
     *
     * @param map
     * @return
     */
    @PostMapping("/user/save")
    public int saveUser(@RequestBody Map<String, Object> map) {
        return this.userService.saveUser(map);
    }

    /**
     * 根据用户查询其拥有角色
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/role")
    public Set<RoleEntity> findUserRole(@RequestParam("userId") int userId) {
        return this.userService.findUserRole(userId);
    }

    /**
     * 为用户解绑角色
     *
     * @param listMap
     * @return
     */
    @PostMapping("/user/deleteRole")
    public Set<RoleEntity> deleteUserRole(@RequestBody Map<String, Object> listMap) {
        return this.userService.deleteUserRole(listMap);
    }

    /**
     * 为用户绑定角色
     *
     * @param listMap
     * @return
     */
    @PostMapping("/user/userAddRole")
    public Set<RoleEntity> userAddRole(@RequestBody Map<String, Object> listMap) {
        Object userId = listMap.get("userId");
        Object roleId = listMap.get("roleId");
        return this.userService.addUserRole((int) userId, (int) roleId);
    }

    @PostMapping("/userc/changePassword")
    public String changePassword(HttpServletRequest request, HttpSession session, HttpServletResponse response){
        return this.userService.changePwd(request,session,response);
    }

    /**
     * 跳转到修改密码页面
     *
     * @return
     */
    @GetMapping(value = "/userc/change")
    public ModelAndView turnToUserChangeManagement() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("./authorityManagement/changePassWord");
        return mv;
    }

    /**
     * 用户未绑定的角色
     * @param userId
     * @return
     */
    @GetMapping("/user/userUnRole")
    public List<RoleEntity> findUserUnRole(@RequestParam("userId") int userId) {
        return this.userService.userUnRole(userId);
    }

    /**
     * 跳转到userManagement页面
     *
     * @return
     */
    @GetMapping(value = "/user/userManagement")
    public ModelAndView turnToUserManagement() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("./authorityManagement/userMgr");
        return mv;
    }
}