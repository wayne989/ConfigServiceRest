package com.example.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * this is the main class to bootstrap the SpringBoot application
 * use EnableJpaAuditing to show more logs on JPA activities
 */
@SpringBootApplication
@EnableJpaAuditing
public class ConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigApplication.class, args);
	}
}
