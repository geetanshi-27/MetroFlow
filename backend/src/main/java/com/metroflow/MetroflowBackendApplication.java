package com.metroflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MetroflowBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetroflowBackendApplication.class, args);
	}

}
