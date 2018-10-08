package com.sv.mc.controller;

import com.sv.mc.pojo.PermissionEntity;
import com.sv.mc.pojo.RoleEntity;
import com.sv.mc.service.RoleService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色控制层
 */
@RestController
public class RoleController {
    @Resource
    RoleService roleService;

    /**
     * 根据名字查询角色
     *
     * @param roleName 角色名称
     * @return 角色信息
     */
    @PostMapping("/role/findByName")
    public RoleEntity findRoleByRoleName(@RequestParam("roleName") String roleName) {
        return this.roleService.findRoleByRoleName(roleName);
    }

    /**
     * 查询所有角色
     * @return 角色信息
     */
    @GetMapping("/role/all")
    public List<RoleEntity> findAllRole() {
        return this.roleService.findAllRole();
    }

    /**
     * 保存角色
     * @param role 保存的角色信息
     * @return 角色信息对象
     */
    @PostMapping("/role/save")
    public RoleEntity saveRole(@RequestBody RoleEntity role) {
        return this.roleService.saveRole(role);
    }

    /**
     * 更新角色
     * @param role 修改的角色信息
     * @return 角色内容
     */
    @PostMapping("/role/update")
    public RoleEntity updateRole(@RequestBody RoleEntity role) {
        return this.roleService.updateRole(role);
    }

    /**
     * 删除角色
     * @param role 删除的角色信息
     */
    @PostMapping("/role/deleteRole")
    public void deleteRole(@RequestBody RoleEntity role) {
        this.roleService.deleteRole(role);
    }

    /**
     * 通过id查询角色
     * @param roleId 角色id
     * @return 角色对象
     */
    @PostMapping("/role/findRoleById")
    public RoleEntity findRoleById(@RequestParam("roleId") int roleId) {
        return this.roleService.findRoleById(roleId);
    }

    /**
     * 查询角色所拥有的所有权限
     * @param roleId 角色Id
     * @return 角色所有的权限内容
     */
    @GetMapping("/role/findRolePermission")
    public Set<PermissionEntity> findRolePermission(@RequestParam("roleId") int roleId) {
        return this.roleService.findRolePermission(roleId);
    }

    /**
     * 查询角色所有未绑定权限
     * @param roleId 角色Id
     * @return 未绑定的权限内容
     */
    @GetMapping("/role/findRoleUnPermission")
    public List<PermissionEntity> findRoleUnPermission(@RequestParam("roleId") int roleId) {
        return this.roleService.findRoleUnPermission(roleId);
    }

    /**
     * 删除当前角色的权限
     * @param mapList 需要删除的权限
     * @return  删除的权限
     */
    @PostMapping("/role/roleDeletePermission")
    public Set<PermissionEntity> roleDeletePermission(@RequestBody Map<String, Object> mapList) {
        Object roleId = mapList.get("roleId");
        Object pId = mapList.get("pId");
        return this.roleService.roleDeletePermission((int) roleId, (int) pId);
    }

    /**
     * 为角色绑定权限
     * @param mapList 绑定的权限信息
     * @return  绑定的权限集合
     */
    @PostMapping("/role/roleAddPermission")
    public Set<PermissionEntity> roleAddPermission(@RequestBody Map<String, Object> mapList) {
        Object roleId = mapList.get("roleId");
        Object pId = mapList.get("pId");
        return this.roleService.roleAddPermission((int) roleId, (int) pId);
    }

    /**
     * 跳转到roleManagement页面
     * @return 权限页面view 层
     */
    @GetMapping(value = "/role/roleManagement")
    public ModelAndView turnToUserManagement() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("./authorityManagement/roleMgr");
        return mv;
    }
}
