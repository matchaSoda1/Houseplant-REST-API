package com.matchasoda.plantsDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlantsDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlantsDemoApplication.class, args);
	}

}
