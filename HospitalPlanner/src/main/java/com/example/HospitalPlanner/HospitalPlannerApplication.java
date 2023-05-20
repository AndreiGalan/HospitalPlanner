package com.example.HospitalPlanner;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class HospitalPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalPlannerApplication.class, args);
	}
}
