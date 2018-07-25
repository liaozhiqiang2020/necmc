package com.sv.mc.service.impl;

import com.sv.mc.pojo.RoleEntity;
import com.sv.mc.pojo.UserEntity;
import com.sv.mc.repository.RoleRepository;
import com.sv.mc.repository.UserRepository;
import com.sv.mc.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
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

    @Override
    @Transactional
    public List<UserEntity> findEntitiesPager() {
        return null;
    }

    @Override
    @Transactional
    public List<UserEntity> findAllUser() {
       return this.userRepository.findAll();
    }

    @Override
    @Transactional
    public List<UserEntity> findAllByStatus() {
        return this.userRepository.findAllByStatus();
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