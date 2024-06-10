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

            // below is fantasy data
            repo.save(new Subject(0, "Algoritmos e Estruturas de Dados", "AED", "ACH2025", "Sistemas de Informação", "Roberto Felipe Dias Ferreira"));
            repo.save(new Subject(0, "Banco de Dados", "BD", "ACH2036", "Sistemas de Informação", "Fernando Masanori Ashikaga"));
            repo.save(new Subject(0, "Redes de Computadores", "RC", "ACH2047", "Sistemas de Informação", "Ney Laert Vilar Calazans"));
            repo.save(new Subject(0, "Engenharia de Software", "ES", "ACH2058", "Sistemas de Informação", "Aurélio Faustino Hoppe"));
            repo.save(new Subject(0, "Sistemas Operacionais", "SO", "ACH2070", "Sistemas de Informação", "Eduardo Viegas"));
            repo.save(new Subject(0, "Matemática Discreta", "MD", "ACH2081", "Sistemas de Informação", "Edson Moreira"));
            repo.save(new Subject(0, "Gestão Empreendedora", "GE", "ACH2092", "Sistemas de Informação", "Fabiano Larentis"));
            repo.save(new Subject(0, "Linguagens de Programação", "LP", "ACH2103", "Sistemas de Informação", "Jeferson Rodrigues da Silva"));
        };
    }

}
