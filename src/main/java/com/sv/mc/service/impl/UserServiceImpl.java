package com.sv.mc.service.impl;

import com.sv.mc.pojo.RoleEntity;
import com.sv.mc.pojo.UserEntity;
import com.sv.mc.repository.PlaceRepository;
import com.sv.mc.repository.RoleRepository;
import com.sv.mc.repository.UserRepository;
import com.sv.mc.repository.VendorRepository;
import com.sv.mc.service.UserService;
import com.sv.mc.util.DateJsonValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService<UserEntity> {

    @Resource
    UserRepository userRepository;

    @Resource
    RoleRepository roleRepository;
    @Resource
    VendorRepository vendorRepository;
    @Resource
    PlaceRepository placeRepository;

    @Override
    @Transactional
    public Page<UserEntity> findEntitiesPager(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public List<UserEntity> findAllUser() {
       return this.userRepository.findAll();
    }

    @Override
    @Transactional
    public String findAllByStatus() {
        List<UserEntity> userList =  this.userRepository.findAllByStatus();
        String company = "";
        String pName = "";
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        config.setExcludes(new String[] { "roleEntitySet"});//红色的部分是过滤掉deviceEntities对象 不转成JSONArray
//        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
//        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        JSONArray jsonArray = JSONArray.fromObject(userList,config);//转化为jsonArray
        JSONArray jsonArray1 = new JSONArray();//新建json数组

        for (int y = 0; y <jsonArray.size() ; y++) {
            JSONObject jsonObject2 =jsonArray.getJSONObject(y);
            int gradeId =Integer.parseInt(jsonObject2.get("gradeId").toString());
            int pId =Integer.parseInt(jsonObject2.get("pId").toString());
            if(pId==1){
                pName = "总公司";
                company = vendorRepository.findHeadNameById(gradeId);
            }else if(pId==2){
                pName = "分公司";
                company = vendorRepository.findBranchNameById(gradeId);
            } else if(pId==3){
                pName = "代理商";
                company = vendorRepository.findVendorById(gradeId).getName();
            } else if(pId ==4){
                pName="场地方";
                company = this.placeRepository.findPlaceById(gradeId).getName();
            }
            jsonObject2.put("company",company);
            jsonObject2.put("pName",pName);
            jsonArray1.add(jsonObject2);
        }
        return jsonArray1.toString();
    }

    @Override
    @Transactional
    public UserEntity updateUser(UserEntity user) {
        user.setAuthenticationString( DigestUtils.md5DigestAsHex(user.getAuthenticationString().getBytes()));
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public UserEntity saveUser(UserEntity user) {
        user.setStatus(1);
        user.setAuthenticationString( DigestUtils.md5DigestAsHex(user.getAuthenticationString().getBytes()));
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(UserEntity user) {
       user.setStatus(0);
       this.userRepository.save(user);
    }

    @Override
    @Transactional
    public UserEntity findUserById(int userId) {
       return this.userRepository.findUserById(userId);
    }

    @Override
    public Set<RoleEntity> findUserRole(int userId) {
        UserEntity user = this.userRepository.findUserById(userId);
        return user.getRoleEntitySet();
    }

    @Override
    public Set<RoleEntity> deleteUserRole(Map<String,Object> listMap) {
        Object userId = listMap.get("userId");
        Object roleIds = listMap.get("roleId");
        UserEntity user = this.userRepository.findUserById((int)userId);
        for (int roleId: (ArrayList<Integer>)roleIds
             ) {
            RoleEntity role = this.roleRepository.findById(roleId);
            user.getRoleEntitySet().remove(role);
        }
        this.userRepository.save(user);
        return user.getRoleEntitySet();
    }

    @Override
    public Set<RoleEntity> addUserRole(int userId, int roleId) {
        UserEntity user = this.userRepository.findUserById(userId);
        RoleEntity role = this.roleRepository.findById(roleId);
        user.getRoleEntitySet().add(role);
        this.userRepository.save(user);
        return user.getRoleEntitySet();
    }

    @Override
    public List<RoleEntity> userUnRole(int userId) {
        UserEntity user = this.userRepository.findUserById(userId);
        List<RoleEntity> roleEntities = this.roleRepository.findAll();
         roleEntities.removeAll(user.getRoleEntitySet());
         return roleEntities;
    }
}