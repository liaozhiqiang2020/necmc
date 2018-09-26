package com.sv.mc.service.impl;

import com.sv.mc.pojo.*;
import com.sv.mc.repository.*;
import com.sv.mc.service.UserService;
import com.sv.mc.util.DateJsonValueProcessor;
import com.sv.mc.util.MD5Util;
import com.sv.mc.util.intUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    @Resource
    BranchRepository branchRepository;

    @Resource
    HeadQuartersRepository headQuartersRepository;

    /**
     * 分页查询所有用户
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    @Transactional
    public String findEntitiesPager(int page, int pageSize) {
        int offset = ((page - 1) * pageSize);
        int total = this.userRepository.findPriceTotal();
        List<UserEntity> userList = this.userRepository.findAllUserByPage(offset, pageSize);
        String company = "";
        String pName = "";
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        config.setExcludes(new String[]{"roleEntitySet"});//红色的部分是过滤掉deviceEntities对象 不转成JSONArray
//        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
//        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        JSONArray jsonArray = JSONArray.fromObject(userList, config);//转化为jsonArray
        JSONArray jsonArray1 = new JSONArray();//新建json数组
        JSONObject jsonObject = new JSONObject();

        for (int y = 0; y < jsonArray.size(); y++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(y);
            int gradeId = Integer.parseInt(jsonObject2.get("gradeId").toString());
            int pId = Integer.parseInt(jsonObject2.get("pId").toString());
            if (gradeId == 1) {
                pName = "总公司";
                if (vendorRepository.findHeadNameById(pId) == null) {
                    company = "";
                }
                {
                    company = vendorRepository.findHeadNameById(pId).getName();
                }
            } else if (gradeId == 2) {
                pName = "分公司";
                if (vendorRepository.findBranchNameById(pId) == null) {
                    company = "";
                } else {
                    company = vendorRepository.findBranchNameById(pId).getName();
                }
            } else if (gradeId == 3) {
                pName = "代理商";
                if (vendorRepository.findVendorById(pId) == null) {
                    company = "";
                } else {
                    company = vendorRepository.findVendorById(pId).getName();
                }
            } else if (gradeId == 4) {
                pName = "场地方";
                if (this.placeRepository.findPlaceById(pId) == null) {
                    company = "";
                } else {
                    company = this.placeRepository.findPlaceById(pId).getName();
                }
            }
            jsonObject2.put("company", company);
            jsonObject2.put("pName", pName);
            jsonArray1.add(jsonObject2);
        }
        jsonObject.put("data", jsonArray1);
        jsonObject.put("total", total);
        return jsonObject.toString();
    }

    /**
     * 查询所有用户
     * @return
     */
    @Override
    @Transactional
    public List<UserEntity> findAllUser() {
        return this.userRepository.findAll();
    }

    /**
     * 查询所有状态为1的用户
     * @param request
     * @return
     */
    @Override
    @Transactional
    public String findAllByStatus(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserEntity user1 = (UserEntity) session.getAttribute("user");
        int uid = user1.getId();
        List<UserEntity> userList = this.userRepository.findAllByStatusId(uid);
        int total = this.userRepository.findPriceTotal();
        String company = "";
        String pName = "";
        JsonConfig config = new JsonConfig();
        config.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        config.setExcludes(new String[]{"roleEntitySet"});//红色的部分是过滤掉deviceEntities对象 不转成JSONArray
//        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
//        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        JSONArray jsonArray = JSONArray.fromObject(userList, config);//转化为jsonArray
        JSONArray jsonArray1 = new JSONArray();//新建json数组
        JSONObject jsonObject = new JSONObject();

        for (int y = 0; y < jsonArray.size(); y++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(y);
            int gradeId = Integer.parseInt(jsonObject2.get("gradeId").toString());
            int pId = Integer.parseInt(jsonObject2.get("pId").toString());
            if (gradeId == 1) {
                pName = "总公司";
                if (vendorRepository.findHeadNameById(pId) == null) {
                    company = "";
                }
                {
                    company = vendorRepository.findHeadNameById(pId).getName();
                }
            } else if (gradeId == 2) {
                pName = "分公司";
                if (vendorRepository.findBranchNameById(pId) == null) {
                    company = "";
                } else {
                    company = vendorRepository.findBranchNameById(pId).getName();
                }
            } else if (gradeId == 3) {
                pName = "代理商";
                if (vendorRepository.findVendorById(pId) == null) {
                    company = "";
                } else {
                    company = vendorRepository.findVendorById(pId).getName();
                }
            } else if (gradeId == 4) {
                pName = "场地方";
                if (this.placeRepository.findPlaceById(pId) == null) {
                    company = "";
                } else {
                    company = this.placeRepository.findPlaceById(pId).getName();
                }
            }
            jsonObject2.put("company", company);
            jsonObject2.put("pName", pName);
            jsonArray1.add(jsonObject2);
        }
        jsonObject.put("data", jsonArray1);
        jsonObject.put("total", total-1);
        return jsonObject.toString();
    }

    /**
     * 更新用户
     * @param map user对象
     * @return
     */
    @Override
    @Transactional
    public int updateUser(Map<String, Object> map) {
        String company = (String) map.get("company");
        String password = (String) map.get("authenticationString");
        String cellphoneNumber = (String) map.get("cellphoneNumber");
        String email = (String) map.get("email");
        int id = (int) map.get("id");
        String fixedPhoneNumber = (String) map.get("fixedPhoneNumber");
        String latestLoginIp = (String) map.get("latestLoginIp");
        String name = (String) map.get("name");
        String userName = (String) map.get("userName");
        int status = (int) map.get("status");
        String createDateTime = (String) map.get("createDatetime");
        String latestLoginDatetime = (String) map.get("latestLoginDatetime");
        String lastTime = intUtil.dateString(latestLoginDatetime);
        UserEntity user = this.userRepository.findUserById(id);
        UserEntity ss = this.userRepository.findUserByUserName(userName);
        if (ss != null ){
            return 0;
        }else {
        user.setStatus(status);
        user.setCellphoneNumber(cellphoneNumber);
        user.setEmail(email);
        user.setFixedPhoneNumber(fixedPhoneNumber);
        user.setName(name);
        user.setUserName(userName);
        user.setLatestLoginIp(latestLoginIp);
//        user.setLatestLoginDatetime(Timestamp.valueOf(lastTime));
//        user.setAuthenticationString(DigestUtils.md5DigestAsHex(password.getBytes()));
        if (password.length() == 32){
            user.setAuthenticationString(password);
        }else {
            user.setAuthenticationString(MD5Util.encode(password));
        }

        if (this.headQuartersRepository.findHByName(company) != null) {
            user.setpId(this.headQuartersRepository.findHByName(company).getId());
            user.setGradeId(1);
        } else if (this.branchRepository.findBByName(company) != null) {
            user.setGradeId(2);
            user.setpId(this.branchRepository.findBByName(company).getId());
        } else if (this.vendorRepository.findVendorEntityByName(company) != null) {
            user.setGradeId(3);
            user.setpId(this.vendorRepository.findVendorEntityByName(company).getId());
        } else if (this.placeRepository.findPByName(company) != null) {
            user.setpId(this.placeRepository.findPByName(company).getId());
            user.setGradeId(4);
        }
        this.userRepository.save(user);
        return 1;
        }
    }

    /**
     * 新建一个用户
     * @param map user对象
     * @return
     */
    @Override
    @Transactional
    public int saveUser(@RequestBody Map<String, Object> map) {
        String company = (String) map.get("company");
        String password = (String) map.get("authenticationString");
        String cellphoneNumber = (String) map.get("cellphoneNumber");
        String email = (String) map.get("email");
        String fixedPhoneNumber = (String) map.get("fixedPhoneNumber");
        String latestLoginIp = (String) map.get("latestLoginIp");
        String name = (String) map.get("name");
        String userName = (String) map.get("userName");
        int status = 1;
        String latestLoginDatetime = (String) map.get("latestLoginDatetime");

        int ss = this.userRepository.findUserName(userName);
        if (ss == 1 ){
            return 0;
        }else {

        UserEntity user = new UserEntity();
        user.setStatus(status);
        user.setCellphoneNumber(cellphoneNumber);
        user.setEmail(email);
        user.setFixedPhoneNumber(fixedPhoneNumber);
        user.setName(name);
        user.setUserName(userName);
        user.setLatestLoginIp(latestLoginIp);
        user.setCreateDatetime(new Timestamp(System.currentTimeMillis()));

        user.setAuthenticationString(MD5Util.encode(password));
        if (this.headQuartersRepository.findHByName(company) != null) {
            user.setpId(this.headQuartersRepository.findHByName(company).getId());
            user.setGradeId(1);
        } else if (this.branchRepository.findBByName(company) != null) {
            user.setGradeId(2);
            user.setpId(this.branchRepository.findBByName(company).getId());
        } else if (this.vendorRepository.findVendorEntityByName(company) != null) {
            user.setGradeId(3);
            user.setpId(this.vendorRepository.findVendorEntityByName(company).getId());
        } else if (this.placeRepository.findPByName(company) != null) {
            user.setpId(this.placeRepository.findPByName(company).getId());
            user.setGradeId(4);
        }
        this.userRepository.save(user);
        return 1;}
    }

    /**
     * 逻辑删除用户
     * @param user 需要删除的User对象
     */
    @Override
    @Transactional
    public void deleteUser(UserEntity user) {
        user.setStatus(0);
        this.userRepository.save(user);
    }

    /**
     * 根据id查询用户
     * @param userId user主键
     * @return
     */
    @Override
    @Transactional
    public UserEntity findUserById(int userId) {
        return this.userRepository.findUserById(userId);
    }

    /**
     * 查询当前用户拥有的所有角色
     * @param userId
     * @return
     */
    @Override
    public Set<RoleEntity> findUserRole(int userId) {
        UserEntity user = this.userRepository.findUserById(userId);
        return user.getRoleEntitySet();
    }

    /**
     * 解绑用户所拥有的角色
     * @param listMap
     * @return
     */
    @Override
    public Set<RoleEntity> deleteUserRole(Map<String, Object> listMap) {
        Object userId = listMap.get("userId");
        Object roleId = listMap.get("roleId");
        UserEntity user = this.userRepository.findUserById((int) userId);

        RoleEntity role = this.roleRepository.findById((int) roleId);
        user.getRoleEntitySet().remove(role);

        this.userRepository.save(user);
        return user.getRoleEntitySet();
    }

    /**
     * 为用户绑定角色
     * @param userId
     * @param roleId
     * @return
     */
    @Override
    public Set<RoleEntity> addUserRole(int userId, int roleId) {
        UserEntity user = this.userRepository.findUserById(userId);
        RoleEntity role = this.roleRepository.findById(roleId);
        user.getRoleEntitySet().add(role);
        this.userRepository.save(user);
        return user.getRoleEntitySet();
    }

    /**
     * 当前用户还为绑定的角色
     * @param userId
     * @return
     */
    @Override
    public List<RoleEntity> userUnRole(int userId) {
        UserEntity user = this.userRepository.findUserById(userId);
        List<RoleEntity> roleEntities = this.roleRepository.findAll();
        roleEntities.removeAll(user.getRoleEntitySet());
        return roleEntities;
    }

    /**
     * 查询所有公司
     * @return
     */
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

    /**
     * 根据公司名字查询所属公司
     * @param gradeId 公司分类
     * @param pId 公司分类
     * @return
     */
    @Override
    public String findCompanyNameByGradeType(int gradeId, int pId) {
        String companyName;
        //总公司
        if (gradeId == 1) {
            companyName = this.headQuartersRepository.findHeadQuartersById(pId).getName();
        }
        //分公司
        else if (gradeId == 2) {
            companyName = this.branchRepository.findBranchById(pId).getName();
        }
        //代理商
        else if (gradeId == 3) {
            companyName = this.vendorRepository.findVendorById(pId).getName();
        }
        //场地方
        else {
            companyName = this.placeRepository.findPlaceById(pId).getName();
        }

        return companyName;
    }

    @Override
    public String changePwd(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String old = request.getParameter("password");
        String new1 = request.getParameter("password1");
        String new2 = request.getParameter("password2");
        String oldPwd = MD5Util.encode(old);
        UserEntity user = (UserEntity) session.getAttribute("user");
        String message = "";
        if (old.equals(new1)){
            message="新密码不能与原密码相同";
        }else if (oldPwd.equals(user.getAuthenticationString())){
            user.setAuthenticationString(MD5Util.encode(new1));
            this.userRepository.save(user);
            message="修改成功,请重新登录";
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null){
                new SecurityContextLogoutHandler().logout(request, response, auth);
            }
        }else if(!oldPwd.equals(user.getAuthenticationString()) ){
            message = "原密码错误";
        }else {
            message = "密码修改失败";
        }
        return message;
    }
}