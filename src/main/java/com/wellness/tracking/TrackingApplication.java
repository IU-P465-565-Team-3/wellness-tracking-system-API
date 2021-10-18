package com.wellness.tracking;

import com.wellness.tracking.model.Role;
import com.wellness.tracking.repository.RoleRepository;
import com.wellness.tracking.service.impl.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackingApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepository repo) {
		return args -> {
			repo.save(new Role(null, "CLIENT"));
			repo.save(new Role(null, "ADMIN"));
			repo.save(new Role(null, "PROFESSIONAL"));
		};
	}
}
