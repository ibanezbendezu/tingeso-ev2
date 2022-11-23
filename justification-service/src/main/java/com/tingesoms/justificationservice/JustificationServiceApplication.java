package com.tingesoms.justificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class JustificationServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(JustificationServiceApplication.class, args);
	}
}
