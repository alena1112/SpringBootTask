package com.task.springboottask;

import com.task.springboottask.services.ProductStorageService;
import com.task.springboottask.services.ProductStorageServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootTaskApplication {

	@Bean
	public ProductStorageService productStorageService() {
		return new ProductStorageServiceImpl();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTaskApplication.class, args);
	}

}
