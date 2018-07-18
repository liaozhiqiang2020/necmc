package com.sv.mc.controller;

import com.sv.mc.pojo.RoleEntity;
import com.sv.mc.service.RoleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class RoleController {
    @Resource
    RoleService roleService;

    @PostMapping("/role/findByName")
    public RoleEntity findRoleByRoleName(@RequestParam("roleName") String roleName){
        return this.roleService.findRoleByRoleName(roleName);
    }

    @GetMapping("/role/all")
    public List<RoleEntity> findAllRole(){
        return this.roleService.findAllRole();
    }

    @PostMapping("/role/save")
    public RoleEntity saveRole(@RequestBody RoleEntity role){
        return this.roleService.saveRole(role);
    }

    @PostMapping("/role/update")
    public RoleEntity updateRole(@RequestBody RoleEntity role){
        return this.roleService.updateRole(role);
    }

    @PostMapping("/role/deleteRole")
    public void deleteRole(@RequestBody RoleEntity role){
        this.roleService.deleteRole(role);
    }

    @PostMapping("/role/findRoleById")
    public RoleEntity findRoleById(@RequestParam("roleId") int roleId){
        return this.roleService.findRoleById(roleId);
    }

    /**
     * 跳转到userManagement页面
     * @return
     */
    @GetMapping(value = "/roleManagement")
    public ModelAndView turnToUserManagement() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("./authorityManagement/roleMgr");
        return mv;
    }
}
