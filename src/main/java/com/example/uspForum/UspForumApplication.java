package com.example.uspForum;

import com.example.uspForum.util.DateHandler;
import com.example.uspForum.util.ModelMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UspForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(UspForumApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public DateHandler dateHandler() {
		return new DateHandler();
	}

}
