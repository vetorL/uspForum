package com.example.uspForum;

import com.example.uspForum.controller.SubjectReviewController;
import com.example.uspForum.model.*;
import com.example.uspForum.repository.*;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UspForumApplication.class})
@WebAppConfiguration
public class SubjectReviewControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SubjectReviewRepository subjectReviewRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesSubjectReviewController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertInstanceOf(MockServletContext.class, servletContext);
        assertNotNull(webApplicationContext.getBean(SubjectReviewController.class));
    }

    @Nested
    class HttpRequestsTests {

        @BeforeEach
        void populateDB() {
            Campus each = campusRepository.save(new Campus("Escola de Artes, Ciências e Humanidades",
                    "EACH"));

            Course si = courseRepository.save(new Course("Sistemas de Informação",
                    "sistemas-de-informacao", each));

            CustomUser usr = customUserRepository.save(new CustomUser("test@test.com", "test",
                    "password", each));

            Professor violeta = professorRepository.save(new Professor("Violeta Sun", "violeta-sun",
                    "violeta@usp.br", each));

            Subject iaecVioleta = subjectRepository.save(new Subject("Introdução à Administração e Economia para Computação",
                    "IAEC", "ACH2063", si, violeta));

            SubjectReview subjectReview = subjectReviewRepository.save(new SubjectReview(usr, iaecVioleta, "Foi bom",
                    "Nao teve prova, mas teve varios trabalhinhos", "Recomendo"));
        }

        @Test
        @WithMockUser(username = "test")
        @DisplayName("Test that the review gets deleted when user who made the DELETE request is the author.")
        void deleteSuccessfulWhenUserIsAuthor() throws Exception {
            long subjectReviewId = 1L;

            mockMvc.perform(delete("/api/v1/reviews/" + subjectReviewId).with(csrf()))
                    .andExpect(status().isNoContent());
        }

        @AfterEach
        void clearDB() {
            // The calling order of deleteAll in these repositories is important because of referential constraint

            subjectReviewRepository.deleteAll();
            subjectRepository.deleteAll();
            customUserRepository.deleteAll();
            professorRepository.deleteAll();
            courseRepository.deleteAll();
            campusRepository.deleteAll();
        }

    }
}
