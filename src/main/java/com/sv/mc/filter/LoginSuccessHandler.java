package com.sv.mc.filter;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sv.mc.pojo.UserEntity;
import com.sv.mc.repository.UserRepository;
import com.sv.mc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LoginSuccessHandler extends
        SavedRequestAwareAuthenticationSuccessHandler {

    private Map<String, String> authDispatcherMap;
    private RequestCache requestCache = new HttpSessionRequestCache();


    //TODO 修改为使用Service
    @Resource
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException,
            ServletException {
        // 获取用户权限
        Collection<? extends GrantedAuthority> authCollection = authentication
                .getAuthorities();


        //获得授权后可得到用户信息   可使用SUserService进行数据库操作
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        /* Set<SysRole> roles = userDetails.getSysRoles();*/
        //TODO 修改为使用Service
        UserEntity user = userRepository.findUserByUserName(userDetails.getUsername());
        user.setLatestLoginDatetime(new Timestamp(System.currentTimeMillis()));
        String ip = this.getIpAddress(request);
        String companyName = this.userService.findCompanyNameByGradeType(user.getGradeId(), user.getpId());
        user.setLatestLoginIp(ip);
        this.userRepository.save(user);
        HttpSession session = request.getSession();
        session.setAttribute("user",user);
        session.setAttribute("userName",user.getName());
        session.setAttribute("companyName", companyName);

        String url = null;
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        String aa = savedRequest.getRedirectUrl();
        System.out.println(aa);
        System.out.println(savedRequest);

        if(savedRequest != null){
            url = savedRequest.getRedirectUrl();
        }
        if(url == null){
            getRedirectStrategy().sendRedirect(request,response,"/index");
        }


        super.onAuthenticationSuccess(request, response, authentication);
    }

    public RequestCache getRequestCache() {
        return requestCache;
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }


    public Map<String, String> getAuthDispatcherMap() {
        return authDispatcherMap;
    }

    public void setAuthDispatcherMap(Map<String, String> authDispatcherMap) {
        this.authDispatcherMap = authDispatcherMap;
    }


    public String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}