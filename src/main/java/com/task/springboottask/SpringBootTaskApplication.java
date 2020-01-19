package com.task.springboottask;

import com.task.springboottask.rest.RequestFilter;
import com.task.springboottask.services.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

@SpringBootApplication
public class SpringBootTaskApplication {

	@Bean
	public ProductStorageService productStorageService() {
		return new ProductStorageServiceImpl();
	}

	@Bean
	public TokenGeneration tokenGeneration() {
	    return new JWTTokenGenerationImpl();
    }

    @Bean
    public UserDetailsService userDetailsService() {
	    return new UserDetailsServiceImpl();
    }

    @Bean
    public OncePerRequestFilter jwtRequestFilter() {
	    return new RequestFilter();
    }

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTaskApplication.class, args);
	}

}
