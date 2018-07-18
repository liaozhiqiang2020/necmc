package com.sv.mc.controller;

import com.sv.mc.pojo.UserEntity;
import com.sv.mc.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {

    @Resource
    UserService userService;

    /**
     * 查询所有user
     * @return 返回User集合
     */
    @GetMapping("/user/all")
    public List<UserEntity> findUserAll(){
        return this.userService.findAllUser();
    }

    /**
     * 查询所有user
     * @return 返回User集合
     */
    @GetMapping("/user/allStatus")
    public List<UserEntity> findAllByStatus(){
        return this.userService.findAllByStatus();
    }

    /**
     * 根据id查询当前用户
     * @param userId 当前用户id
     * @return 用户信息
     */
    @GetMapping("/user/currentUser")
    public UserEntity findUserById (@RequestParam("userId") int userId){
        return this.userService.findUserById(userId);
    }

    /**
     * 逻辑删除用户
     * @param userEntity 用户对象
     */
    @PostMapping("/user/delete")
    public void deleteUser(@RequestBody UserEntity userEntity){
        this.userService.deleteUser(userEntity);
    }

    /**
     * 更新用户
     * @param userEntity
     * @return
     */
    @PostMapping("/user/update")
    public UserEntity updateUser (@RequestBody UserEntity userEntity){
        return this.userService.updateUser(userEntity);
    }

    /**
     * 保存用户
     * @param userEntity
     * @return
     */
    @PostMapping("/user/save")
    public UserEntity saveUser (@RequestBody UserEntity userEntity){
        return this.userService.saveUser(userEntity);
    }

    /**
     * 跳转到userManagement页面
     * @return
     */
    @GetMapping(value = "/userManagement")
    public ModelAndView turnToUserManagement() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("./authorityManagement/userMgr");
        return mv;
    }
}