package com.sv.triangle.service.impl;

import com.sv.triangle.exception.UserRolePermissionDuplicatedBindingException;
import com.sv.triangle.pojo.RoleEntity;
import com.sv.triangle.repository.RoleRepository;
import com.sv.triangle.repository.UserRepository;
import com.sv.triangle.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
