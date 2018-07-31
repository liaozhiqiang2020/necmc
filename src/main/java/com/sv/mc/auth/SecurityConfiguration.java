package com.sv.mc.auth;

import com.sv.mc.filter.LoginSuccessHandler;
import com.sv.mc.filter.MyFilterSecurityInterceptor;
import com.sv.mc.service.impl.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled =true)
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
                .antMatchers("/fonts/**")
                .antMatchers("/img/**")
                .antMatchers("/js/**");
        super.configure(web);
    }

        @Override
        protected void configure(HttpSecurity http) throws Exception{
            http
                    .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                    .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/**").authenticated()
//                    .antMatchers("/**").authenticated()
//                    .antMatchers("/css/**").permitAll()
//                    .antMatchers("/js/**").permitAll()
//                    .antMatchers("/img/**").permitAll()
//                    .antMatchers("/price/**").hasAuthority("ROLE_AD")
//                    .antMatchers("/role/**").hasAuthority("总公司权限")
//                    .antMatchers("/user/**").hasAuthority("一级管理")
//                    .anyRequest().permitAll()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    .successForwardUrl("/index")
                    .permitAll()
                    .successHandler(loginSuccessHandler())
                    .and()
                    .logout()
//                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
                    .invalidateHttpSession(true);
//            http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        }


        @Autowired
                public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
            auth.userDetailsService(myUserDetailService);
        }


    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

}