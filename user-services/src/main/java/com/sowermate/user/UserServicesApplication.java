package com.sowermate.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.sowermate",exclude = SecurityAutoConfiguration.class)
public class UserServicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServicesApplication.class,args);
    }
}