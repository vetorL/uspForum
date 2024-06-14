package com.example.uspForum.config;

import com.example.uspForum.model.Campus;
import com.example.uspForum.model.Subject;
import com.example.uspForum.repository.CampusRepository;
import com.example.uspForum.repository.SubjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("dev")
@Configuration
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner dataLoader(SubjectRepository subjectRepo, CampusRepository campusRepo) {
        return args -> {
            Campus each = campusRepo.save(new Campus(0, "Escola de Artes, Ciências e Humanidades", "EACH"));

            subjectRepo.save(new Subject(0, "Introdução à Administração e Economia para Computação",
                    "IAEC", "ACH2063", "Sistemas de Informação",
                    each, "Violeta Sun"));

            subjectRepo.save(new Subject(0, "Computação Orientada a Objetos", "COO", "ACH2003",
                    "Sistemas de Informação", each, "Flavio Luiz Coutinho"));
            subjectRepo.save(new Subject(0, "Fundamentos de Sistemas de Informação", "FSI", "ACH2014", "Sistemas de Informação", each, "Edmir Parada Vasques Prado"));

            // below is fantasy data
//            subjectRepo.save(new Subject(0, "Algoritmos e Estruturas de Dados", "AED", "ACH2025", "Sistemas de Informação", "São Carlos", "Roberto Felipe Dias Ferreira"));
//            subjectRepo.save(new Subject(0, "Banco de Dados", "BD", "ACH2036", "Sistemas de Informação", "São Carlos", "Fernando Masanori Ashikaga"));
//            subjectRepo.save(new Subject(0, "Redes de Computadores", "RC", "ACH2047", "Sistemas de Informação", "São Carlos", "Ney Laert Vilar Calazans"));
//            subjectRepo.save(new Subject(0, "Engenharia de Software", "ES", "ACH2058", "Sistemas de Informação", "São Carlos", "Aurélio Faustino Hoppe"));
//            subjectRepo.save(new Subject(0, "Sistemas Operacionais", "SO", "ACH2070", "Sistemas de Informação", "São Carlos", "Eduardo Viegas"));
//            subjectRepo.save(new Subject(0, "Matemática Discreta", "MD", "ACH2081", "Sistemas de Informação", "São Carlos", "Edson Moreira"));
//            subjectRepo.save(new Subject(0, "Gestão Empreendedora", "GE", "ACH2092", "Sistemas de Informação", "São Carlos", "Fabiano Larentis"));
//            subjectRepo.save(new Subject(0, "Linguagens de Programação", "LP", "ACH2103", "Sistemas de Informação", "São Carlos", "Jeferson Rodrigues da Silva"));
        };
    }

}
