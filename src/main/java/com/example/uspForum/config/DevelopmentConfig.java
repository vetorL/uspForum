package com.example.uspForum.config;

import com.example.uspForum.model.*;
import com.example.uspForum.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;

@Profile("dev")
@Configuration
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner dataLoader(SubjectRepository subjectRepo,
                                        CampusRepository campusRepo,
                                        SubjectReviewRepository subjectReviewRepo,
                                        ProfessorRepository professorRepo,
                                        CourseRepository courseRepo, CustomUserRepository customUserRepository,
                                        PasswordEncoder passwordEncoder) {
        return args -> {
            Campus each = campusRepo.save(new Campus(0, "Escola de Artes, Ciências e Humanidades", "EACH"));
            Campus eca = campusRepo.save(new Campus(0, "Escola de Comunicações e Artes", "ECA"));
            Campus eefe = campusRepo.save(new Campus(0, "Escola de Educação Física e Esporte", "EEFE"));
            Campus ee = campusRepo.save(new Campus(0, "Escola de Enfermagem", "EE"));
            Campus poli = campusRepo.save(new Campus(0, "Escola Politécnica", "Poli"));
            Campus fau = campusRepo.save(new Campus(0, "Faculdade de Arquitetura e Urbanismo", "FAU"));
            Campus fcf = campusRepo.save(new Campus(0, "Faculdade de Ciências Farmacêuticas", "FCF"));
            Campus fd = campusRepo.save(new Campus(0, "Faculdade de Direito", "FD"));
            Campus fea = campusRepo.save(new Campus(0, "Faculdade de Economia, Administração, Contabilidade e Atuária", "FEA"));
            Campus fe = campusRepo.save(new Campus(0, "Faculdade de Educação", "FE"));
            Campus fflch = campusRepo.save(new Campus(0, "Faculdade de Filosofia, Letras e Ciências Humanas", "FFLCH"));
            Campus fm = campusRepo.save(new Campus(0, "Faculdade de Medicina", "FM"));
            Campus fmvz = campusRepo.save(new Campus(0, "Faculdade de Medicina Veterinária e Zootecnia", "FMVZ"));
            Campus fo = campusRepo.save(new Campus(0, "Faculdade de Odontologia", "FO"));
            Campus fsp = campusRepo.save(new Campus(0, "Faculdade de Saúde Pública", "FSP"));
            Campus iag = campusRepo.save(new Campus(0, "Instituto de Astronomia, Geofísica e Ciências Atmosféricas", "IAG"));
            Campus ib = campusRepo.save(new Campus(0, "Instituto de Biociências", "IB"));
            Campus icb = campusRepo.save(new Campus(0, "Instituto de Ciências Biomédicas", "ICB"));
            Campus iee = campusRepo.save(new Campus(0, "Instituto de Energia e Ambiente", "IEE"));
            Campus iea = campusRepo.save(new Campus(0, "Instituto de Estudos Avançados", "IEA"));
            Campus ieb = campusRepo.save(new Campus(0, "Instituto de Estudos Brasileiros", "IEB"));
            Campus ifCampus = campusRepo.save(new Campus(0, "Instituto de Física", "IF"));
            Campus igc = campusRepo.save(new Campus(0, "Instituto de Geociências", "IGc"));
            Campus ime = campusRepo.save(new Campus(0, "Instituto de Matemática e Estatística", "IME"));
            Campus imt = campusRepo.save(new Campus(0, "Instituto de Medicina Tropical de São Paulo", "IMT"));
            Campus ip = campusRepo.save(new Campus(0, "Instituto de Psicologia", "IP"));
            Campus iq = campusRepo.save(new Campus(0, "Instituto de Química", "IQ"));
            Campus iri = campusRepo.save(new Campus(0, "Instituto de Relações Internacionais", "IRI"));
            Campus io = campusRepo.save(new Campus(0, "Instituto Oceanográfico", "IO"));
            Campus fob = campusRepo.save(new Campus(0, "Faculdade de Odontologia de Bauru", "FOB"));
            Campus eel = campusRepo.save(new Campus(0, "Escola de Engenharia de Lorena", "EEL"));
            Campus eeferp = campusRepo.save(new Campus(0, "Escola de Educação Física e Esporte de Ribeirão Preto", "EEFERP"));
            Campus eerpf = campusRepo.save(new Campus(0, "Escola de Enfermagem de Ribeirão Preto", "EERP"));
            Campus fcfrp = campusRepo.save(new Campus(0, "Faculdade de Ciências Farmacêuticas de Ribeirão Preto", "FCFRP"));
            Campus fdrp = campusRepo.save(new Campus(0, "Faculdade de Direito de Ribeirão Preto", "FDRP"));
            Campus fearp = campusRepo.save(new Campus(0, "Faculdade de Economia, Administração e Contabilidade de Ribeirão Preto", "FEARP"));
            Campus ffclrp = campusRepo.save(new Campus(0, "Faculdade de Filosofia, Ciências e Letras de Ribeirão Preto", "FFCLRP"));
            Campus fmrp = campusRepo.save(new Campus(0, "Faculdade de Medicina de Ribeirão Preto", "FMRP"));
            Campus forp = campusRepo.save(new Campus(0, "Faculdade de Odontologia de Ribeirão Preto", "FORP"));
            Campus cena = campusRepo.save(new Campus(0, "Centro de Energia Nuclear na Agricultura", "CENA"));
            Campus fzea = campusRepo.save(new Campus(0, "Faculdade de Zootecnia e Engenharia de Alimentos", "FZEA"));

            Course si = courseRepo.save(new Course(0, "Sistemas de Informação", each));

            CustomUser usr = customUserRepository.save(new CustomUser(0, "test@test.com", "test", passwordEncoder.encode("test"), new ArrayList<>()));

            SubjectReview iaecReview = subjectReviewRepo.save(new SubjectReview(0, usr, "Foi bom", "Nao teve prova, mas teve varios trabalhinhos", 0));
            SubjectReview cooReview = subjectReviewRepo.save(new SubjectReview(0, usr, "Foi OK", "O conteudo foi passado com grande rigorosidade, mas atraves de slides.", 0));
            SubjectReview fsiReview = subjectReviewRepo.save(new SubjectReview(0, usr, "STELLAR", "Prova online e tudo mais", 0));

            Professor violeta = professorRepo.save(new Professor(0, "Violeta Sun", "violeta@usp.br"));
            Professor edmir = professorRepo.save(new Professor(0, "Edmir Parada Vasques Prado", "eprado@usp.br"));
            Professor coutinho = professorRepo.save(new Professor(0, "Flavio Luiz Coutinho", "flcoutinho@usp.br"));

            subjectRepo.save(new Subject(0, "Introdução à Administração e Economia para Computação",
                    "IAEC", "ACH2063", si, violeta, Arrays.asList(iaecReview)));

            subjectRepo.save(new Subject(0, "Computação Orientada a Objetos", "COO", "ACH2003",
                    si, coutinho, Arrays.asList(cooReview)));
            subjectRepo.save(new Subject(0, "Fundamentos de Sistemas de Informação", "FSI", "ACH2014",
                    si, edmir, Arrays.asList(fsiReview)));

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
