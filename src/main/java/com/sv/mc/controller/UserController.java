package com.sv.mc.controller;

import com.google.gson.*;
import com.sv.mc.pojo.UserEntity;
import com.sv.mc.pojo.sysUserEntity;
import com.sv.mc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

@RestController("/user")
public class UserController {

    @Resource
    UserService userService;

    /**
     * 查询所有user
     * @return 返回User集合
     */
    @GetMapping("/all")
    public List<UserEntity> findUserAll(){
        return this.userService.findAllUser();
    }

    /**
     * 查询所有user
     * @return 返回User集合
     */
    @GetMapping("/allStatus")
    public List<UserEntity> findAllByStatus(){
        return this.userService.findAllByStatus();
    }

    /**
     * 根据id查询当前用户
     * @param userId 当前用户id
     * @return 用户信息
     */
    @GetMapping("/currentUser")
    public UserEntity findUserById (@RequestParam("userId") int userId){
        return this.userService.findUserById(userId);
    }

    /**
     * 逻辑删除用户
     * @param userEntity 用户对象
     */
    @PostMapping("/delete")
    public void deleteUser(@RequestBody UserEntity userEntity){
        this.userService.deleteUser(userEntity);
    }

    /**
     * 更新或保存用户
     * @param userEntity
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public UserEntity saveOrUpdateUser (@RequestBody UserEntity userEntity){
        return this.saveOrUpdateUser(userEntity);
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