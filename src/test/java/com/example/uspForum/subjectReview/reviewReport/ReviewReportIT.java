package com.example.uspForum.subjectReview.reviewReport;

import com.example.uspForum.UspForumApplication;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UspForumApplication.class})
@WebAppConfiguration
public class ReviewReportIT {

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
    private ReviewReportRepository reviewReportRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @BeforeEach
    void populateDB() throws Exception {
        // # Populate DB
        Campus each = campusRepository.save(new Campus("Escola de Artes, Ciências e Humanidades",
                "EACH"));

        Course si = courseRepository.save(new Course("Sistemas de Informação",
                "sistemas-de-informacao", each));

        CustomUser usr = customUserRepository.save(new CustomUser("email@email", "email",
                "aaaaaaaa"));

        Professor violeta = professorRepository.save(new Professor("Violeta Sun", "violeta-sun",
                "violeta@usp.br", each));

        Subject iaecVioleta = subjectRepository.save(new Subject("Introdução à Administração e Economia para Computação",
                "IAEC", "ACH2063", si, violeta));

        subjectReviewRepository.save(new SubjectReview(usr, iaecVioleta, "Foi bom",
                "Nao teve prova, mas teve varios trabalhinhos", "Recomendo"));
    }

    @Test
    @DirtiesContext
    public void givenWac_whenServletContext_thenItProvidesSubjectReviewController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertInstanceOf(MockServletContext.class, servletContext);
        assertNotNull(webApplicationContext.getBean(ReviewReportController.class));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    @DisplayName("Report successful when user authenticated")
    void reportSuccessfulWhenUserAuthenticated() throws Exception {
        // # Given
        long subjectReviewId = 1L;
        ReviewReportDTO reviewReportDTO = new ReviewReportDTO("Outro");

        // # Check that there are no reports in the DB
        assertEquals(0, reviewReportRepository.count());

        // # Perform request
        mockMvc.perform(post("/api/v1/reviews/" + subjectReviewId + "/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewReportDTO))
                        .with(csrf()))
                .andExpect(status().isCreated());

        // # Check that a report has been added to the DB
        assertEquals(1, reviewReportRepository.count());
    }

    @Test
    @DirtiesContext
    @WithAnonymousUser
    @DisplayName("Report unsuccessful when user unauthenticated")
    void reportUnsuccessfulWhenUserUnauthenticated() throws Exception {
        // # Given
        long subjectReviewId = 1L;
        ReviewReportDTO reviewReportDTO = new ReviewReportDTO("reason");

        // # Check that there are no reports in the DB
        assertEquals(0, reviewReportRepository.count());

        // NOTE: this test class is not configured with SecurityConfig, thus it does not block the request at the
        // controller level. What happens is that when the controller delegates to the service layer the
        // @PreAuthorize blocks the request due to not being authenticated.
        try {

            // # Perform request
            mockMvc.perform(post("/api/v1/reviews/" + subjectReviewId + "/report")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reviewReportDTO))
                    .with(csrf()));

        } catch (Exception e) {

            // # Check that the correct exception was thrown
            assertInstanceOf(ServletException.class, e);

        }

        // # Check that no report has been added to the DB
        assertEquals(0, reviewReportRepository.count());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    @DisplayName("Reporting twice does not throw exception")
    void reportingTwiceDoesNotThrowException() throws Exception {
        // # Given
        long subjectReviewId = 1L;
        ReviewReportDTO reviewReportDTO = new ReviewReportDTO("Outro");

        // # Check that there are no reports in the DB
        assertEquals(0, reviewReportRepository.count());

        // # Perform first request
        mockMvc.perform(post("/api/v1/reviews/" + subjectReviewId + "/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewReportDTO))
                        .with(csrf()))
                .andExpect(status().isCreated());

        // # Perform second identical request
        mockMvc.perform(post("/api/v1/reviews/" + subjectReviewId + "/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewReportDTO))
                        .with(csrf()))
                .andExpect(status().isCreated());

        // # Check that there is only one report in the DB
        assertEquals(1, reviewReportRepository.count());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    @DisplayName("Reporting unsuccessful when reason invalid")
    void reportingUnsuccessfulWhenReasonInvalid() throws Exception {
        // # Given
        long subjectReviewId = 1L;
        ReviewReportDTO reviewReportDTO = new ReviewReportDTO("Invalid");

        // # Check that there are no reports in the DB
        assertEquals(0, reviewReportRepository.count());

        // # Perform request
        mockMvc.perform(post("/api/v1/reviews/" + subjectReviewId + "/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewReportDTO))
                        .with(csrf()))
                .andExpect(status().isBadRequest());

        // # Check that no report has been added to the DB
        assertEquals(0, reviewReportRepository.count());
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
        reviewReportRepository.deleteAll();
    }

}
