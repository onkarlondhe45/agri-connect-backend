package com.agriconnect;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AgriConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgriConnectApplication.class, args);
		System.out.println("Application running on port number 9090...!");
	}

	@Bean
	GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder().group("public").pathsToMatch("/**").build();
	}
}
