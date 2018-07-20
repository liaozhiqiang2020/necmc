package com.sv.mc.service.impl;

import com.sv.mc.pojo.PermissionEntity;
import com.sv.mc.pojo.UserEntity;
import com.sv.mc.repository.PermissionRepository;
import com.sv.mc.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private PermissionRepository permissionRepository;

    //登陆验证时，通过username获取用户的所有权限信息，
    //并返回User放到spring的全局缓存SecurityContextHolder中，以供授权器使用
    public UserDetails loadUserByUsername(String username){
        UserEntity user = userRepository.findUserByUserName(username);
        if(user != null){
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            List<PermissionEntity> permissions = this.permissionRepository.findPermissionByUser(user.getId());
            for(PermissionEntity permission : permissions) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getPermissionsName());
                grantedAuthorities.add(grantedAuthority);
            }
            return new User(user.getUserName(), user.getAuthenticationString(), grantedAuthorities);
        }else {
            throw new UsernameNotFoundException("admin: "+ username +" do not exist!");
        }
    }
}