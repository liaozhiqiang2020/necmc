package com.sv.mc.auth;

import com.sv.mc.filter.LoginSuccessHandler;
import com.sv.mc.filter.MyFilterSecurityInterceptor;
import com.sv.mc.service.impl.MyUserDetailService;
import com.sv.mc.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private MyUserDetailService myUserDetailService;
    @Resource
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    @Resource
    private LoginSuccessHandler loginSuccessHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring()
                .antMatchers("/css/**")
                .antMatchers("/static/**")
                .antMatchers("/fonts/**")
                .antMatchers("/img/**")
                .antMatchers("/js/**")
                .antMatchers("/.well-known/**")
                .antMatchers("/weixin/**")
                .antMatchers("/alipay/**")
                .antMatchers("/wx/**")
                .antMatchers("/JMNomfSJJT.txt")
                .antMatchers("/wx/JMNomfSJJT.txt");
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //在权限校验框架之前添加过滤器
                .cors().and()
                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .antMatchers("/index").permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .permitAll()
                .successForwardUrl("/index")
                .successHandler(loginSuccessHandler())
                .and()
                .logout()
//                    .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .invalidateHttpSession(true);
//            http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }


//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(myUserDetailService);
//    }

    //权限校验框架登录校验
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService).passwordEncoder(new PasswordEncoder(){

            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String)rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.encode((String)rawPassword));
            }}); //user Details Service验证
    }



    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }



}