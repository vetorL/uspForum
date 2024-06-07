package com.example.uspForum;

import com.example.uspForum.model.Subject;
import com.example.uspForum.repository.SubjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UspForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(UspForumApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(SubjectRepository repo) {
		return args -> {
			repo.save(new Subject(0, "Introdução à Administração e Economia para Computação","IAEC", "ACH2063", "Sistemas de Informação", "Violeta Sun"));
		};
	}

}
