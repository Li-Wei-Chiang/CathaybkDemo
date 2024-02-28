package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@EnableScheduling
@OpenAPIDefinition(info = @Info(title = "Wesley Demo", version = "v1.0.0"))
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws Exception {		
		SpringApplication.run(DemoApplication.class, args);

	}


}