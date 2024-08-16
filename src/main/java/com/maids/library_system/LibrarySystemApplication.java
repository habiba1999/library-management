package com.maids.library_system;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
@OpenAPIDefinition(info = @Info(
		title = "Library Management APIs",
		version = "1.0.0"))
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class LibrarySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarySystemApplication.class, args);
	}

}
