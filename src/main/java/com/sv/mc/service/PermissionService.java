package com.sv.mc.service;

import com.sv.mc.pojo.PermissionEntity;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限管理逻辑层
 * @author 魏帅志
 */
public interface PermissionService {

    PermissionEntity findPermissionByPermissionName(String PermissionName);
    /**
     * 分页查询所有Permission
     * @return 分页集合
     */
    List<PermissionEntity> findEntitiesPager();

    /**
     * 不分页查询所有Permission
     * @return 所有Permission
     */
    List<PermissionEntity> findAllPermission();

    /**
     *更新Permission对象
     * @param Permission Permission对象
     * @return 更新的Permission对象
     */
    PermissionEntity updatePermission(PermissionEntity Permission);
    /**
     *保存Permission对象
     * @param Permission Permission对象
     * @return 保存的Permission对象
     */
    PermissionEntity savePermission(PermissionEntity Permission);


    /**
     * 根据Id查询Permission
     * @param PermissionId Permission主键
     * @return 查询结果
     */
    PermissionEntity findPermissionById(int PermissionId);
}
