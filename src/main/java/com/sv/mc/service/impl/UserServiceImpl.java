package com.sv.mc.service.impl;

import com.sv.mc.pojo.*;
import com.sv.mc.repository.*;
import com.sv.mc.service.UserService;
import com.sv.mc.util.DateJsonValueProcessor;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Resource
    BranchRepository branchRepository;

    @Resource
    HeadQuartersRepository headQuartersRepository;

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
            if(gradeId==1){
                pName = "总公司";
                if(vendorRepository.findHeadNameById(pId) == null){
                    company = "";
                }{
                company = vendorRepository.findHeadNameById(pId).getName();}
            }else if(gradeId==2){
                pName = "分公司";
                if(vendorRepository.findBranchNameById(pId) == null){
                    company = "";
                }else {
                company = vendorRepository.findBranchNameById(pId).getName();}
            } else if(gradeId==3){
                pName = "代理商";
                if(vendorRepository.findVendorById(pId) == null){
                    company = "";
                }else {
                company = vendorRepository.findVendorById(pId).getName();}
            } else if(gradeId ==4){
                pName="场地方";
                if(this.placeRepository.findPlaceById(pId) == null){
                    company = "";
                }else {
                company = this.placeRepository.findPlaceById(pId).getName();}
            }
            jsonObject2.put("company",company);
            jsonObject2.put("pName",pName);
            jsonArray1.add(jsonObject2);
        }
        return jsonArray1.toString();
    }

    @Override
    @Transactional
    public UserEntity updateUser(Map<String,Object> map) {
        String company = (String) map.get("company");
        String password = (String) map.get("authenticationString");
        String cellphoneNumber = (String) map.get("cellphoneNumber");
        String email = (String) map.get("email");
        int id = (int) map.get("id");
        String fixedPhoneNumber = (String) map.get("fixedPhoneNumber");
        String latestLoginIp = (String)map.get("latestLoginIp");
        String name = (String)map.get("name");
        String userName = (String)map.get("userName");
        int status = (int)map.get("status");
        String createDateTime = (String) map.get("createDatetime");
        String latestLoginDatetime = (String) map.get("latestLoginDatetime");

        UserEntity user = this.userRepository.findUserById(id);
        user.setStatus(status);
        user.setCellphoneNumber(cellphoneNumber);
        user.setEmail(email);
        user.setFixedPhoneNumber(fixedPhoneNumber);
        user.setName(name);
        user.setUserName(userName);
        user.setLatestLoginIp(latestLoginIp);
        user.setAuthenticationString( DigestUtils.md5DigestAsHex(password.getBytes()));
        if(this.headQuartersRepository.findHByName(company)!=null){
            user.setpId(this.headQuartersRepository.findHByName(company).getId());
            user.setGradeId(1);
        }else if (this.branchRepository.findBByName(company)!=null){
            user.setGradeId(2);
            user.setpId(this.branchRepository.findBByName(company).getId());
        }
        else if (this.vendorRepository.findVendorEntityByName(company) != null){
            user.setGradeId(3);
            user.setpId(this.vendorRepository.findVendorEntityByName(company).getId());
        }else if(this.placeRepository.findPByName(company)!=null){
            user.setpId(this.placeRepository.findPByName(company).getId());
            user.setGradeId(4);
        }
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public UserEntity saveUser(@RequestBody Map<String,Object> map) {
        String company = (String) map.get("company");
        String password = (String) map.get("authenticationString");
        String cellphoneNumber = (String) map.get("cellphoneNumber");
        String email = (String) map.get("email");
        String fixedPhoneNumber = (String) map.get("fixedPhoneNumber");
        String latestLoginIp = (String)map.get("latestLoginIp");
        String name = (String)map.get("name");
        String userName = (String)map.get("userName");
        int status = 1;
        String latestLoginDatetime = (String) map.get("latestLoginDatetime");

        UserEntity user = new UserEntity();
        user.setStatus(status);
        user.setCellphoneNumber(cellphoneNumber);
        user.setEmail(email);
        user.setFixedPhoneNumber(fixedPhoneNumber);
        user.setName(name);
        user.setUserName(userName);
        user.setLatestLoginIp(latestLoginIp);
        user.setCreateDatetime(new Timestamp(System.currentTimeMillis()));
        user.setLatestLoginDatetime(new Timestamp(System.currentTimeMillis()));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            Date date1 = simpleDateFormat.parse(createDateTime);
//            Date date2 = simpleDateFormat.parse(latestLoginDatetime);
//            long timeStamp1 = date1.getTime();
//            long timeStamp2 = date2.getTime();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            user.setCreateDatetime( (Timestamp) map.get("latestLoginDatetime"));
//        }catch (ParseException e){
//            System.out.println(e.getMessage());
//        }

        user.setAuthenticationString( DigestUtils.md5DigestAsHex(password.getBytes()));
        if(this.headQuartersRepository.findHByName(company)!=null){
            user.setpId(this.headQuartersRepository.findHByName(company).getId());
            user.setGradeId(1);
        }else if (this.branchRepository.findBByName(company)!=null){
            user.setGradeId(2);
            user.setpId(this.branchRepository.findBByName(company).getId());
        }
        else if (this.vendorRepository.findVendorEntityByName(company) != null){
            user.setGradeId(3);
            user.setpId(this.vendorRepository.findVendorEntityByName(company).getId());
        }else if(this.placeRepository.findPByName(company)!=null){
            user.setpId(this.placeRepository.findPByName(company).getId());
            user.setGradeId(4);
        }
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

    @Override
    public List<Object> findAllplace() {
        List<BranchEntity> branch = this.branchRepository.findAll();
        List<VendorEntity> vendor = this.vendorRepository.findAll();
        List<PlaceEntity> place = this.placeRepository.findAll();
        List<HeadQuartersEntity> head = this.headQuartersRepository.findAllHead();
        List<Object> list = new ArrayList<>();
        list.addAll(branch);
        list.addAll(vendor);
        list.addAll(head);
        list.addAll(place);
        return list;
    }
}