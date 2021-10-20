package com.wellness.tracking;

import com.wellness.tracking.model.Role;
import com.wellness.tracking.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WellnessTrackingAPI {

	public static void main(String[] args) {
		SpringApplication.run(WellnessTrackingAPI.class, args);
	}
}
