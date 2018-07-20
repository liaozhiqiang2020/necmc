//package com.sv.mc.filter;
//
//import com.sv.mc.pojo.PermissionEntity;
//import com.sv.mc.repository.PermissionRepository;
//import groovy.util.logging.Log4j;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//
//@Service
//@Log4j
//public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
//
//    @Resource
//    PermissionRepository permissionRepository;
//    private HashMap<String, Collection<ConfigAttribute>> map =null;
//
//    //tomcat启动时实例化一次
////    public MyInvocationSecurityMetadataSource() {
////        loadResourceDefine();
////    }
//
//    /**
//     * 加载资源，初始化资源变量
//     */
//    public void loadResourceDefine(){
//        map = new HashMap<>();
//        Collection<ConfigAttribute> array;
//        ConfigAttribute cfg;
//        List<PermissionEntity> permissions = permissionRepository.findAll();
//        for(PermissionEntity permission : permissions) {
//            array = new ArrayList<>();
//            cfg = new SecurityConfig(permission.getPermissionsName());
//            array.add(cfg);
//            map.put(permission.getUrl(), array);
//        }
////        log.info("security info load success!!");
//    }
//
//
//    /**
//     * 根据路径获取访问权限的集合接口
//     *
//     * @paramobject
//     * @return
//     * @throwsIllegalArgumentException
//     */
//    @Override
//    public Collection<ConfigAttribute>getAttributes(Object object)
//    throws IllegalArgumentException {
//        if(map ==null) loadResourceDefine();
//        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
//        AntPathRequestMatcher matcher;
//        String resUrl;
//        for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
//            resUrl = iter.next();
//            matcher = new AntPathRequestMatcher(resUrl);
//            if(matcher.matches(request)) {
//                return map.get(resUrl);
//            }
//        }
//        return null;
//    }
//
//    /**
//     * @return
//     */
//    @Override
//    public Collection<ConfigAttribute>getAllConfigAttributes(){
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz){
//        return true;
//    }
//}