package com.matchasoda.plantTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlantTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlantTrackerApplication.class, args);
	}

}
