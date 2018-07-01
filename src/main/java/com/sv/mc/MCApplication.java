package com.sv.mc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@Configuration
@ComponentScan
@ImportResource(locations={"classpath:spring-redis.xml"})
@EnableCaching
@EnableAutoConfiguration
public class MCApplication {

	public static void main(String[] args) {
		SpringApplication.run(MCApplication.class, args);

	}
}
