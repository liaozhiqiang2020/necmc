package com.sv.mc.service.impl;

import com.sv.mc.exception.UserRolePermissionDuplicatedBindingException;
import com.sv.mc.repository.RoleRepository;
import com.sv.mc.repository.UserRepository;
import com.sv.mc.service.RoleService;
import com.sv.mc.pojo.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List findAllEntities() {
        return this.roleRepository.findAll();
    }

    @Override
    @Transactional
    public void save(RoleEntity roleEntity) throws UserRolePermissionDuplicatedBindingException {
        try {
            this.roleRepository.save(roleEntity);
        }
        catch (Exception e){
            throw new UserRolePermissionDuplicatedBindingException("对不起，该用户已经与当前角色绑定了，不能重复绑定。" + e.getMessage());
        }
    }

    @Override
    public RoleEntity findRoleByRoleName(String roleName) {
        return this.roleRepository.findRoleByRoleName(roleName);
    }

    @Override
    public void removeUserFromRole(String roleName, String userName) {
        RoleEntity role =  this.roleRepository.findRoleByRoleName(roleName);
        role.getUserEntities().remove(this.userRepository.findUserByUserName(userName));
        this.roleRepository.save(role);
    }
}
