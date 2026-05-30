package com.koinovio.management_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class  ManagementServiceApplication {
	//todo change db credentials to env variable
	public static void main(String[] args) {
		SpringApplication.run(ManagementServiceApplication.class, args);
	}

}
