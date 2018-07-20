package com.sv.mc.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled =true)
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//    @Resource
//    private MyUserDetailService myUserDetailService;
//    @Resource
//    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;


        @Override
        protected void configure(HttpSecurity http) throws Exception{
            http
                    .csrf().disable();
//                    .authorizeRequests()
//                    .antMatchers("/css/**").permitAll()
//                    .antMatchers("/js/**").permitAll()
//                    .antMatchers("/img/**").permitAll()
//                    .anyRequest().permitAll()
//                    .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .failureUrl("/login?error")
//                    .successForwardUrl("/priceTest")
//                    .permitAll()
//                    .and()
//                    .logout()
//                    .logoutUrl("/user")
//                    .logoutSuccessUrl("/user")
//                    .permitAll();
//            http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        }

//        @Autowired
//                public void configureGlobal(AuthenticationManagerBuilder auth)throws Exception{
//            auth.userDetailsService(myUserDetailService);
//        }
//
//    @Bean
//    public static NoOpPasswordEncoder passwordEncoder() {
//        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**").antMatchers("/fonts/**").antMatchers("/img/**").antMatchers("/js/**");
    }


}