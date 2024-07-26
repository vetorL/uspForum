package com.example.uspForum;

import com.example.uspForum.campus.Campus;
import com.example.uspForum.campus.CampusRepository;
import com.example.uspForum.subjectReview.SubjectReviewController;
import com.example.uspForum.course.Course;
import com.example.uspForum.course.CourseRepository;
import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.customUser.CustomUserRepository;
import com.example.uspForum.subject.Subject;
import com.example.uspForum.subject.SubjectRepository;
import com.example.uspForum.subjectReview.SubjectReview;
import com.example.uspForum.subjectReview.SubjectReviewDTO;
import com.example.uspForum.professor.Professor;
import com.example.uspForum.professor.ProfessorRepository;
import com.example.uspForum.subjectReview.SubjectReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    private ObjectMapper objectMapper;

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

        @Test
        @DirtiesContext
        @WithAnonymousUser
        @DisplayName("Test that the review does not get deleted when an " +
                "anonymous user makes the DELETE request.")
        void deleteFailWhenUserAnonymous() throws Exception {
            long subjectReviewId = 1L;

            // Check that before the DELETE request the subjectReview is present in the DB
            Optional<SubjectReview> subjectReview = subjectReviewRepository.findById(subjectReviewId);
            assertTrue(subjectReview.isPresent());

            // NOTE: this test class is not configured with SecurityConfig, thus it does not block the request at the
            // controller level. What happens is that when the controller delegates to the service layer the
            // @PreAuthorize tries to access the authentication principal and fails, because it does not exist for
            // an anonymous user, and so the delete method does not get executed, leading to a double layer of
            // protection.
            try {
                mockMvc.perform(delete("/api/v1/reviews/" + subjectReview.get().getId()).with(csrf()));
            } catch (Exception e) {
                assertInstanceOf(IllegalArgumentException.class, e.getCause());
            }

            // Check that after the DELETE request the subjectReview is still present in the DB
            assertTrue(subjectReviewRepository.findById(subjectReview.get().getId()).isPresent());
        }

        @Test
        @DirtiesContext
        @WithMockUser(username = "test")
        @DisplayName("Test that the review gets edited when user who made the request is the author.")
        void editSuccessfulWhenUserIsAuthor() throws Exception {
            long subjectReviewId = 1L;
            SubjectReviewDTO subjectReviewDTO = new SubjectReviewDTO("Edited Title", "Edited Content",
                    "Neutro");

            // Check that before the request the subjectReview is present in the DB
            Optional<SubjectReview> oldSubjectReview = subjectReviewRepository.findById(subjectReviewId);
            assertTrue(oldSubjectReview.isPresent());

            // Perform request
            mockMvc.perform(patch("/api/v1/reviews/" + oldSubjectReview.get().getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(subjectReviewDTO))
                            .with(csrf())
                    )
                    .andExpect(status().isOk())
                    .andReturn();

            // Check that the subjectReview has been updated
            Optional<SubjectReview> updatedSubjectReview = subjectReviewRepository.findById(subjectReviewId);
            assertTrue(updatedSubjectReview.isPresent());
            assertEquals(subjectReviewDTO.getTitle(), updatedSubjectReview.get().getTitle());
            assertEquals(subjectReviewDTO.getContent(), updatedSubjectReview.get().getContent());
            assertEquals(subjectReviewDTO.getRecommendation(), updatedSubjectReview.get().getRecommendation());
        }

        @Test
        @DirtiesContext
        @WithMockUser(username = "notTheAuthor")
        @DisplayName("Test that the review does not get edited when the " +
                "user who made the PUT request is not the author.")
        void editFailWhenUserNotAuthor() throws Exception {
            long subjectReviewId = 1L;
            SubjectReviewDTO subjectReviewDTO = new SubjectReviewDTO("Edited Title", "Edited Content",
                    "Neutro");

            // Check that before the PUT request the subjectReview is present in the DB
            Optional<SubjectReview> subjectReview = subjectReviewRepository.findById(subjectReviewId);
            assertTrue(subjectReview.isPresent());

            try {
                mockMvc.perform(put("/api/v1/reviews/" + subjectReview.get().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectReviewDTO))
                        .with(csrf())
                );
            } catch (Exception e) {
                assertInstanceOf(AccessDeniedException.class, e.getCause());
            }

            // Check that after the PUT request the subject review is still present in the DB
            assertTrue(subjectReviewRepository.findById(subjectReview.get().getId()).isPresent());

        }

        @Test
        @DirtiesContext
        @WithAnonymousUser
        @DisplayName("Test that the review does not get edited when an " +
                "anonymous user makes the PUT request.")
        void editFailWhenUserAnonymous() throws Exception {
            long subjectReviewId = 1L;
            SubjectReviewDTO subjectReviewDTO = new SubjectReviewDTO("Edited Title", "Edited Content",
                    "Neutro");

            // Check that before the PUT request the subjectReview is present in the DB
            Optional<SubjectReview> subjectReview = subjectReviewRepository.findById(subjectReviewId);
            assertTrue(subjectReview.isPresent());

            // NOTE: this test class is not configured with SecurityConfig, thus it does not block the request at the
            // controller level. What happens is that when the controller delegates to the service layer the
            // @PreAuthorize tries to access the authentication principal and fails, because it does not exist for
            // an anonymous user, and so the deleteAndCreate method does not get executed, leading to a double layer of
            // protection.
            try {
                mockMvc.perform(put("/api/v1/reviews/" + subjectReview.get().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectReviewDTO))
                        .with(csrf())
                );
            } catch (Exception e) {
                assertInstanceOf(IllegalArgumentException.class, e.getCause());
            }

            // Check that after the PUT request the subject review is still present in the DB
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
