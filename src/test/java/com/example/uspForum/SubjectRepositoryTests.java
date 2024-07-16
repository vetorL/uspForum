package com.example.uspForum;

import com.example.uspForum.model.Campus;
import com.example.uspForum.model.Course;
import com.example.uspForum.model.Professor;
import com.example.uspForum.model.Subject;
import com.example.uspForum.repository.CampusRepository;
import com.example.uspForum.repository.CourseRepository;
import com.example.uspForum.repository.ProfessorRepository;
import com.example.uspForum.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class SubjectRepositoryTests {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @BeforeEach
    void setUp() {
        Campus each = campusRepository
                .save(new Campus("Escola de Artes, Ciências e Humanidades", "EACH"));

        Course si = courseRepository
                .save(new Course("Sistemas de Informação", "sistemas-de-informacao", each));

        Professor violeta = professorRepository
                .save(new Professor("Violeta Sun", "violeta-sun", "violeta@usp.br", each));

        Subject iaecVioleta = new Subject("Introdução à Administração e Economia para Computação",
                "IAEC", "ACH2063", si, violeta);

        subjectRepository.save(iaecVioleta);
    }

    @Test
    void testSameNameAndProfessorAndCourseException() throws Exception {
        Course si = courseRepository.findById(1L).orElseThrow(() -> new RuntimeException("Course not found"));
        Professor violeta = professorRepository.findById(1L).orElseThrow(() -> new RuntimeException("Professor not found"));

        Subject iaecVioleta = new Subject("Introdução à Administração e Economia para Computação",
                "IAEC", "ACH2063", si, violeta);

        assertThrows(DataIntegrityViolationException.class, () -> {
            subjectRepository.saveAndFlush(iaecVioleta);
        });

        // I am convinced this thing wasn't made to be tested... xD
        // Reference this page in case of headaches!
        // https://stackoverflow.com/questions/54876448/how-to-catch-hibernate-constraintviolationexception-or-spring-dataintegrityviol?rq=2
    }

//    @AfterEach
//    void tearDown() {
//        subjectRepository.deleteAll();
//    }

}
