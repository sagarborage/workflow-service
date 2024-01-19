package com.sowermate.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.sowermate.flexipunch"}, exclude = {SecurityAutoConfiguration.class})
public class NotificationServicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServicesApplication.class, args);
    }

}