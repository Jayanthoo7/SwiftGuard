package com.jayanth.swiftguard_engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SwiftguardEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwiftguardEngineApplication.class, args);
	}
}
