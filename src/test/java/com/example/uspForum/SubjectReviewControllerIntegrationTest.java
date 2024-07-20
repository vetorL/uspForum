package com.example.uspForum;

import com.example.uspForum.controller.SubjectReviewController;
import com.example.uspForum.model.*;
import com.example.uspForum.repository.*;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        @DirtiesContext
        @WithMockUser(username = "test")
        @DisplayName("Test that the review gets deleted when user who made the DELETE request is the author.")
        void deleteSuccessfulWhenUserIsAuthor() throws Exception {
            long subjectReviewId = 1L;

            // Check that before the DELETE request the subjectReview is present in the DB
            Optional<SubjectReview> subjectReview = subjectReviewRepository.findById(subjectReviewId);
            assertTrue(subjectReview.isPresent());

            mockMvc.perform(delete("/api/v1/reviews/" + subjectReview.get().getId()).with(csrf()))
                    .andExpect(status().isNoContent());

            // Check that after the DELETE request the subjectReview is no longer present in the DB
            assertEquals(Optional.empty(), subjectReviewRepository.findById(subjectReview.get().getId()));
        }

        @Test
        @DirtiesContext
        @WithMockUser(username = "notTheAuthor")
        @DisplayName("Test that the review does not get deleted when the " +
                "user who made the DELETE request is not the author.")
        void deleteFailWhenUserNotAuthor() throws Exception {
            long subjectReviewId = 1L;

            // Check that before the DELETE request the subjectReview is present in the DB
            Optional<SubjectReview> subjectReview = subjectReviewRepository.findById(subjectReviewId);
            assertTrue(subjectReview.isPresent());

            try {
                mockMvc.perform(delete("/api/v1/reviews/" + subjectReview.get().getId()).with(csrf()));
            } catch (Exception e) {
                assertInstanceOf(AccessDeniedException.class, e.getCause());
            }

            // Check that after the DELETE request the subjectReview is still present in the DB
            assertTrue(subjectReviewRepository.findById(subjectReview.get().getId()).isPresent());
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
