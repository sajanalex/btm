package com.breakthemould;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Break The Mould Pas-sport API",
				description = "API defenitions of the Pas-sport services",
				version = "1.0.0"))
public class BreakthemouldApplication {

	public static void main(String[] args) {
		SpringApplication.run(BreakthemouldApplication.class, args);
	}

}
