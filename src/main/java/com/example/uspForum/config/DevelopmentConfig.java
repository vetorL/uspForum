package com.example.uspForum.config;

import com.example.uspForum.model.Subject;
import com.example.uspForum.repository.SubjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner dataLoader(SubjectRepository repo) {
        return args -> {
            repo.save(new Subject(0, "Introdução à Administração e Economia para Computação","IAEC", "ACH2063", "Sistemas de Informação", "Violeta Sun"));
            repo.save(new Subject(0, "Computação Orientada a Objetos", "COO", "ACH2003", "Sistemas de Informação", "Flavio Luiz Coutinho"));
            repo.save(new Subject(0, "Fundamentos de Sistemas de Informação", "FSI", "ACH2014", "Sistemas de Informação", "Edmir Parada Vasques Prado"));
        };
    }

}
