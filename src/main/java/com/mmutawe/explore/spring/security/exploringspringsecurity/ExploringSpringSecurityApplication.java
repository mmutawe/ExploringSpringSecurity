package com.mmutawe.explore.spring.security.exploringspringsecurity;

import com.mmutawe.explore.spring.security.exploringspringsecurity.mocks.MockDataService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExploringSpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExploringSpringSecurityApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> {
			MockDataService.CLIENTS.forEach(System.out::println);
		};
	}

}
