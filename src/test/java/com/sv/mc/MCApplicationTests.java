package com.sv.mc;

import com.sv.mc.exception.UserRolePermissionDuplicatedBindingException;
import com.sv.mc.pojo.PermissionEntity;
import com.sv.mc.pojo.RoleEntity;
import com.sv.mc.pojo.UserEntity;
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
        List<RoleEntity> roleEntityList = this.roleService.findAllEntities();
        for (RoleEntity roleEntity : roleEntityList) {
            for (UserEntity userEntity : roleEntity.getUserEntities()) {
                System.out.println("---------------------------------------------------------------------------" + userEntity.getUserName());
            }
        }
    }

    @Test
    public void testFindByUser() {
        List<UserEntity> userEntityList = this.userService.findAllEntities();
        for (UserEntity userEntity : userEntityList) {
            for (RoleEntity roleEntity : userEntity.getRoleEntities()) {
                System.out.println("当前用户：" + userEntity.getUserName() + "    " + "当前用户所在角色：" + roleEntity.getRoleName());
            }
        }
    }

    @Test
    @Rollback(false)
    public void testAddUserToRole() {
        RoleEntity roleEntity = this.roleService.findRoleByRoleName("普通用户");
        UserEntity userEntity = this.userService.findUserByUserName("kangkai");
        roleEntity.getUserEntities().add(userEntity);
        try {
            this.roleService.save(roleEntity);
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
//        RoleEntity roleEntity = this.roleService.findRoleByRoleName("普通用户");
//
//        PermissionEntity permissionEntity = new PermissionEntity();
//        permissionEntity.getRoleEntities().add(roleEntity);
//        permissionEntity.setId(100);
//        permissionEntity.setName("7级");
//        permissionEntity.setDescription("dsfdsf");
//        permissionEntity.setUrl("www.baidu.com");
//        bo.save(permissionEntity);
        PermissionEntity permissionEntity = bo.findByPermissionName("7级");
        bo.delet(permissionEntity);

//     PermissionEntity s = bo.findByPermissionName("7级");
//       System.out.println("---------------------------------------------------------------------------" +s.getDescription()+s.getName()+s.getUrl()+s.getId());

    }

    @Test
    public void test3(){
        UserEntity user = userService.findUserByUserName("kangkai");
        user.getUserName();
        redisBo.add("test-3",user.getUserName());

    }
//	@Test
//	@Transactional
//	@Rollback(value = false)
//	public void saveUser() {
//		UserEntity userEntity = new UserEntity();
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
