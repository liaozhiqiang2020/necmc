package com.sv.mc.conf;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
@Configuration
public class UploadConfig {
    //自定义修改临时路径
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("/fileUpload/");
        return factory.createMultipartConfig();
    }
}
