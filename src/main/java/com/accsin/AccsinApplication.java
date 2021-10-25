package com.accsin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AccsinApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccsinApplication.class, args);
	}
}