package com.clinic.anhe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AnheApplication {

	//adding another comment for rebasing 2
	public static void main(String[] args) {
		SpringApplication.run(AnheApplication.class, args);
	}
}
