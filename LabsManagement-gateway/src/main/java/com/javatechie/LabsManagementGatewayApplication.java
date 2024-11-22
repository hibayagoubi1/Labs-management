package com.javatechie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class LabsManagementGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LabsManagementGatewayApplication.class, args);
	}

}
