package com.sowermate.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(scanBasePackages = {"com.sowermate"}, exclude = {SecurityAutoConfiguration.class})
public class WorkFlowAppServicesApplication {
	public static void main(String[] args) {
		SpringApplication.run(WorkFlowAppServicesApplication.class, args);
	}

}
