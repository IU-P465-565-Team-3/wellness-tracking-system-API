package com.wellness.tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WellnessTrackingAPI {

	public static void main(String[] args) {
		SpringApplication.run(WellnessTrackingAPI.class, args);
	}
}
