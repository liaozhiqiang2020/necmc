package com.sv.mc;

import com.sv.mc.exception.UserRolePermissionDuplicatedBindingException;
import com.sv.mc.pojo.sysPermissionEntity;
import com.sv.mc.pojo.sysRoleEntity;
import com.sv.mc.pojo.sysUserEntity;
import com.sv.mc.service.PermissionService;
import com.sv.mc.service.RedisService;
import com.sv.mc.service.RoleService;
import com.sv.mc.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MCApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Resource
    private PermissionService bo;

    @Resource
    private RedisService redisBo;

    @Test
    public void testFindByRole() {
        List<sysRoleEntity> sysRoleEntityList = this.roleService.findAllEntities();
        for (sysRoleEntity sysRoleEntity : sysRoleEntityList) {
            for (sysUserEntity sysUserEntity : sysRoleEntity.getUserEntities()) {
                System.out.println("---------------------------------------------------------------------------" + sysUserEntity.getUserName());
            }
        }
    }

    @Test
    public void testFindByUser() {
        List<sysUserEntity> sysUserEntityList = this.userService.findAllEntities();
        for (sysUserEntity sysUserEntity : sysUserEntityList) {
            for (sysRoleEntity sysRoleEntity : sysUserEntity.getRoleEntities()) {
                System.out.println("当前用户：" + sysUserEntity.getUserName() + "    " + "当前用户所在角色：" + sysRoleEntity.getRoleName());
            }
        }
    }

    @Test
    @Rollback(false)
    public void testAddUserToRole() {
        sysRoleEntity sysRoleEntity = this.roleService.findRoleByRoleName("普通用户");
        sysUserEntity sysUserEntity = this.userService.findUserByUserName("kangkai");
        sysRoleEntity.getUserEntities().add(sysUserEntity);
        try {
            this.roleService.save(sysRoleEntity);
        } catch (UserRolePermissionDuplicatedBindingException e) {
            e.printStackTrace();
        }
    }

    @Test
    @Rollback(false)
    public void testRemoveUserFromRole() {
        this.roleService.removeUserFromRole("普通用户", "kangkai");
    }


    @Test
    public void test1(){
//        sysRoleEntity roleEntity = this.roleService.findRoleByRoleName("普通用户");
//
//        sysPermissionEntity sysPermissionEntity = new sysPermissionEntity();
//        sysPermissionEntity.getRoleEntities().add(roleEntity);
//        sysPermissionEntity.setId(100);
//        sysPermissionEntity.setName("7级");
//        sysPermissionEntity.setDescription("dsfdsf");
//        sysPermissionEntity.setUrl("www.baidu.com");
//        bo.save(sysPermissionEntity);
        sysPermissionEntity sysPermissionEntity = bo.findByPermissionName("7级");
        bo.delet(sysPermissionEntity);

//     sysPermissionEntity s = bo.findByPermissionName("7级");
//       System.out.println("---------------------------------------------------------------------------" +s.getDescription()+s.getName()+s.getUrl()+s.getId());

    }

    @Test
    public void test3(){
        sysUserEntity user = userService.findUserByUserName("kangkai");
        user.getUserName();
        redisBo.add("test-3",user.getUserName());

    }
//	@Test
//	@Transactional
//	@Rollback(value = false)
//	public void saveUser() {
//		sysUserEntity userEntity = new sysUserEntity();
//		userEntity.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
//		userEntity.setLatestLoginDateTime(new Timestamp(System.currentTimeMillis()));
//		userEntity.setCellPhoneNumber("13901183126");
//		userEntity.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
//		userEntity.setEmail("kangkai@sailvast.com");
//		userEntity.setPassword("123456");
//		userEntity.setQq("1747080");
//		userEntity.setUsername("kangkai1");
//		this.userService.saveOrUpdate(userEntity);
//	}
//
//	@Test
//	@Transactional
//	@Rollback(value = false)
//	public void saveGroup(){
//		GroupEntity groupEntity = new GroupEntity();
//		groupEntity.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
//		groupEntity.setGroupName("管理员");
//	}

}
