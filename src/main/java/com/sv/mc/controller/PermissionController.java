package com.sv.mc.controller;

import com.sv.mc.pojo.PermissionEntity;
import com.sv.mc.service.PermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    /**
     * 查询所有权限
     * @return
     */
    @GetMapping("/permission/all")
    List<PermissionEntity> findAll() {
        return this.permissionService.findAllPermission();
    }

    /**
     * 更新权限
     * @param permissionEntity
     * @return
     */
    @PostMapping("/permission/update")
    PermissionEntity updatePermission(@RequestBody PermissionEntity permissionEntity) {
        return this.permissionService.updatePermission(permissionEntity);
    }

    /**
     * 保存权限
     * @param permissionEntity
     * @return
     */
    @PostMapping("/permission/save")
    PermissionEntity savePermission(@RequestBody PermissionEntity permissionEntity) {
        return this.permissionService.savePermission(permissionEntity);
    }

    /**
     * 根据名字查询权限
     * @param name
     * @return
     */
    @PostMapping("/permission/findByName")
    PermissionEntity findPermissionByName(@RequestParam("permissionName") String name) {
        return this.permissionService.findPermissionByPermissionName(name);
    }

    /**
     * 根据id查询权限
     * @param id
     * @return
     */
    @GetMapping("/permission/findById")
    PermissionEntity findPermissionById(@RequestParam("permissionId") int id) {
        return this.permissionService.findPermissionById(id);
    }

//    /**
//     * 跳转到permission管理页面
//     * @return
//     */
//    @GetMapping(value = "/user/permissionManagement")
//    public ModelAndView turnToUserManagement() {
//        ModelAndView mv = new ModelAndView();
//
//        mv.setViewName("./authorityManagement/permissionMgr");
//        return mv;
//    }
}
