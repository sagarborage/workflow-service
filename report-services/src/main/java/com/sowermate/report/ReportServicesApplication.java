package com.sowermate.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.sowermate", exclude = SecurityAutoConfiguration.class)
public class ReportServicesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReportServicesApplication.class, args);
    }
}