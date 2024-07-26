package com.example.uspForum.config;

import com.example.uspForum.campus.Campus;
import com.example.uspForum.campus.CampusRepository;
import com.example.uspForum.course.Course;
import com.example.uspForum.course.CourseRepository;
import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.customUser.CustomUserRepository;
import com.example.uspForum.professor.Professor;
import com.example.uspForum.professor.ProfessorRepository;
import com.example.uspForum.subject.Subject;
import com.example.uspForum.subject.SubjectRepository;
import com.example.uspForum.subjectReview.SubjectReview;
import com.example.uspForum.subjectReview.SubjectReviewRepository;
import com.example.uspForum.vote.Vote;
import com.example.uspForum.vote.VoteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("dev")
@Configuration
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner dataLoader(SubjectRepository subjectRepo,
                                        CampusRepository campusRepo,
                                        SubjectReviewRepository subjectReviewRepo,
                                        ProfessorRepository professorRepo,
                                        CourseRepository courseRepo, CustomUserRepository customUserRepository,
                                        PasswordEncoder passwordEncoder,
                                        VoteRepository voteRepository) {
        return args -> {
            Campus each = campusRepo.save(new Campus("Escola de Artes, Ciências e Humanidades", "EACH"));
            Campus eca = campusRepo.save(new Campus("Escola de Comunicações e Artes", "ECA"));
            Campus eefe = campusRepo.save(new Campus("Escola de Educação Física e Esporte", "EEFE"));
            Campus ee = campusRepo.save(new Campus("Escola de Enfermagem", "EE"));
            Campus poli = campusRepo.save(new Campus("Escola Politécnica", "Poli"));
            Campus fau = campusRepo.save(new Campus("Faculdade de Arquitetura e Urbanismo", "FAU"));
            Campus fcf = campusRepo.save(new Campus("Faculdade de Ciências Farmacêuticas", "FCF"));
            Campus fd = campusRepo.save(new Campus("Faculdade de Direito", "FD"));
            Campus fea = campusRepo.save(new Campus("Faculdade de Economia, Administração, Contabilidade e Atuária", "FEA"));
            Campus fe = campusRepo.save(new Campus("Faculdade de Educação", "FE"));
            Campus fflch = campusRepo.save(new Campus("Faculdade de Filosofia, Letras e Ciências Humanas", "FFLCH"));
            Campus fm = campusRepo.save(new Campus("Faculdade de Medicina", "FM"));
            Campus fmvz = campusRepo.save(new Campus("Faculdade de Medicina Veterinária e Zootecnia", "FMVZ"));
            Campus fo = campusRepo.save(new Campus("Faculdade de Odontologia", "FO"));
            Campus fsp = campusRepo.save(new Campus("Faculdade de Saúde Pública", "FSP"));
            Campus iag = campusRepo.save(new Campus("Instituto de Astronomia, Geofísica e Ciências Atmosféricas", "IAG"));
            Campus ib = campusRepo.save(new Campus("Instituto de Biociências", "IB"));
            Campus icb = campusRepo.save(new Campus("Instituto de Ciências Biomédicas", "ICB"));
            Campus iee = campusRepo.save(new Campus("Instituto de Energia e Ambiente", "IEE"));
            Campus iea = campusRepo.save(new Campus("Instituto de Estudos Avançados", "IEA"));
            Campus ieb = campusRepo.save(new Campus("Instituto de Estudos Brasileiros", "IEB"));
            Campus ifCampus = campusRepo.save(new Campus("Instituto de Física", "IF"));
            Campus igc = campusRepo.save(new Campus("Instituto de Geociências", "IGc"));
            Campus ime = campusRepo.save(new Campus("Instituto de Matemática e Estatística", "IME"));
            Campus imt = campusRepo.save(new Campus("Instituto de Medicina Tropical de São Paulo", "IMT"));
            Campus ip = campusRepo.save(new Campus("Instituto de Psicologia", "IP"));
            Campus iq = campusRepo.save(new Campus("Instituto de Química", "IQ"));
            Campus iri = campusRepo.save(new Campus("Instituto de Relações Internacionais", "IRI"));
            Campus io = campusRepo.save(new Campus("Instituto Oceanográfico", "IO"));
            Campus fob = campusRepo.save(new Campus("Faculdade de Odontologia de Bauru", "FOB"));
            Campus eel = campusRepo.save(new Campus("Escola de Engenharia de Lorena", "EEL"));
            Campus eeferp = campusRepo.save(new Campus("Escola de Educação Física e Esporte de Ribeirão Preto", "EEFERP"));
            Campus eerpf = campusRepo.save(new Campus("Escola de Enfermagem de Ribeirão Preto", "EERP"));
            Campus fcfrp = campusRepo.save(new Campus("Faculdade de Ciências Farmacêuticas de Ribeirão Preto", "FCFRP"));
            Campus fdrp = campusRepo.save(new Campus("Faculdade de Direito de Ribeirão Preto", "FDRP"));
            Campus fearp = campusRepo.save(new Campus("Faculdade de Economia, Administração e Contabilidade de Ribeirão Preto", "FEARP"));
            Campus ffclrp = campusRepo.save(new Campus("Faculdade de Filosofia, Ciências e Letras de Ribeirão Preto", "FFCLRP"));
            Campus fmrp = campusRepo.save(new Campus("Faculdade de Medicina de Ribeirão Preto", "FMRP"));
            Campus forp = campusRepo.save(new Campus("Faculdade de Odontologia de Ribeirão Preto", "FORP"));
            Campus cena = campusRepo.save(new Campus("Centro de Energia Nuclear na Agricultura", "CENA"));
            Campus fzea = campusRepo.save(new Campus("Faculdade de Zootecnia e Engenharia de Alimentos", "FZEA"));
            Campus eesc = campusRepo.save(new Campus("Escola de Engenharia de São Carlos", "EESC"));
            Campus iau = campusRepo.save(new Campus("Instituto de Arquitetura e Urbanismo", "IAU"));
            Campus icmc = campusRepo.save(new Campus("Instituto de Ciências Matemáticas e de Computação", "ICMC"));
            Campus ifsc = campusRepo.save(new Campus("Instituto de Física de São Carlos", "IFSC"));
            Campus iqsc = campusRepo.save(new Campus("Instituto de Química de São Carlos", "IQSC"));

            Course si = courseRepo.save(new Course("Sistemas de Informação", "sistemas-de-informacao", each));
            Course siSC = courseRepo.save(new Course("Sistemas de Informação", "sistemas-de-informacao", icmc));

            CustomUser usr = customUserRepository.save(new CustomUser("test@test.com", "test",
                    passwordEncoder.encode("test"), each));
            CustomUser usr2 = customUserRepository.save(new CustomUser("test2@test.com", "test2",
                    passwordEncoder.encode("test2"), poli));
            CustomUser usr3 = customUserRepository.save(new CustomUser("4133737163905494@usp.br", "4133737163905494",
                    passwordEncoder.encode("4133737163905494"), poli));

            Professor violeta = professorRepo.save(
                    new Professor("Violeta Sun", "violeta-sun", "violeta@usp.br", each));
            Professor edmir = professorRepo.save(
                    new Professor("Edmir Parada Vasques Prado", "edmir-parada-vasques-prado",
                            "eprado@usp.br", each));
            Professor coutinho = professorRepo.save(new Professor("Flavio Luiz Coutinho",
                    "flavio-luiz-coutinho", "flcoutinho@usp.br", each));

            Subject iaecVioleta = new Subject("Introdução à Administração e Economia para Computação",
                    "IAEC", "ACH2063", si, violeta);

            Subject coo = new Subject("Computação Orientada a Objetos", "COO", "ACH2003",
                    si, coutinho);

            Subject fsi = new Subject("Fundamentos de Sistemas de Informação", "FSI", "ACH2014",
                    si, edmir);

            Subject subjectIP = new Subject("Introdução à Programação", "IP", "ACH2001", si, coutinho);

            Subject iaecEdmir = new Subject("Introdução à Administração e Economia para Computação",
                    "IAEC", "ACH2063", si, edmir);

            subjectRepo.save(iaecVioleta);
            subjectRepo.save(coo);
            subjectRepo.save(fsi);
            subjectRepo.save(subjectIP);
            subjectRepo.save(iaecEdmir);

            SubjectReview iaecReview =
                    subjectReviewRepo.save(new SubjectReview(usr, iaecVioleta, "Foi bom", "Nao teve prova, mas teve varios trabalhinhos",
                            "Recomendo"));
            SubjectReview cooReview =
                    subjectReviewRepo.save(new SubjectReview(usr, coo,
                            "Foi OK", "O conteudo foi passado com grande rigorosidade, mas atraves de slides.",
                            "Não recomendo"));
            SubjectReview fsiReview =
                    subjectReviewRepo.save(new SubjectReview(usr3, fsi,
                            "But I must explain to you how all this mistaken id",
                            "But I must explain to you how all this mistaken idea of denouncing pleasure and " +
                                    "praising pain was born and I will give you a complete account of the system, and " +
                                    "expound the actual teachings of the great explorer of the truth, the master-builder " +
                                    "of human happiness. No one rejects, dislikes, or avoids pleasure itself, because " +
                                    "it is pleasure, but because those who do not know how to pursue pleasure rationally " +
                                    "encounter consequences that are extremely painful. Nor again is there anyone who " +
                                    "loves or pursues or desires to obtain pain of itself, because it is pain, but because " +
                                    "occasionally circumstances occur in which toil and pain can procure him some great " +
                                    "pleasure. To take a trivial example, which of us ever undertakes laborious physical " +
                                    "exercise, except to obtain some advantage from it? But who has any right to find " +
                                    "fault with a man who chooses to enjoy a pleasure that has no annoying consequences, " +
                                    "or one who avoids a pain that produces no resultant pleasure? On the other hand, we denounc",
                            "Neutro"));

            Vote iaecVote = voteRepository.save(new Vote(-1, usr2, iaecReview));
            Vote fsiVote = voteRepository.save(new Vote(1, usr, fsiReview));
            Vote cooVote = voteRepository.save(new Vote(-1, usr2, cooReview));

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
