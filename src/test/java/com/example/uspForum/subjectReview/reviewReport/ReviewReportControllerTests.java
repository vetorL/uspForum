package com.example.uspForum.subjectReview.reviewReport;

import com.example.uspForum.config.SecurityConfig;
import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.customUser.CustomUserService;
import com.example.uspForum.subjectReview.SubjectReview;
import com.example.uspForum.subjectReview.SubjectReviewService;
import com.example.uspForum.util.ModelMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewReportController.class)
@Import(SecurityConfig.class)
public class ReviewReportControllerTests {

    @MockBean
    private ReviewReportService reviewReportService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private SubjectReviewService subjectReviewService;

    // Necessary because of @Import(SecurityConfig.class)
    @MockBean
    private CustomUserService customUserService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithAnonymousUser
    @DisplayName("Reporting does not work when unauthenticated")
    void reportingDoesNotWorkWhenUnauthenticated() throws Exception {
        // # Given
        ReviewReportDTO reviewReportDTO = new ReviewReportDTO("reason");

        long subjectReviewId = 1L;
        SubjectReview subjectReview = new SubjectReview();
        subjectReview.setId(subjectReviewId);

        mockMvc.perform(post("/api/v1/reviews/" + subjectReviewId + "/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewReportDTO))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

        verifyNoInteractions(modelMapper);
        verifyNoInteractions(subjectReviewService);
        verifyNoInteractions(reviewReportService);
    }

    @Test
    @WithMockUser
    @DisplayName("Reporting does not work when invalid csrf")
    void reportingDoesNotWorkWhenInvalidCsrf() throws Exception {
        // # Given
        ReviewReportDTO reviewReportDTO = new ReviewReportDTO("reason");

        long subjectReviewId = 1L;
        SubjectReview subjectReview = new SubjectReview();
        subjectReview.setId(subjectReviewId);

        mockMvc.perform(post("/api/v1/reviews/" + subjectReviewId + "/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewReportDTO))
                        .with(csrf().useInvalidToken()))
                .andExpect(status().isForbidden());

        verifyNoInteractions(modelMapper);
        verifyNoInteractions(subjectReviewService);
        verifyNoInteractions(reviewReportService);
    }

    @Test
    @WithMockUser
    @DisplayName("Reporting works when authenticated")
    void reportingWorksWhenAuthenticated() throws Exception {
        // # Given
        ReviewReportDTO reviewReportDTO = new ReviewReportDTO("Outro");

        long subjectReviewId = 1L;
        SubjectReview subjectReview = new SubjectReview();
        subjectReview.setId(subjectReviewId);

        // # Mock return value of method calls
        when(subjectReviewService.findById(anyLong())).thenReturn(subjectReview);

        when(modelMapper.toReviewReport(any(ReviewReportDTO.class), any(), any(SubjectReview.class)))
                .thenReturn(new ReviewReport());

        // # Perform request
        mockMvc.perform(post("/api/v1/reviews/" + subjectReviewId + "/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewReportDTO))
                        .with(csrf()))
                .andExpect(status().isCreated());

        // # Verify interactions

        // Interactions with modelMapper
        verify(modelMapper, times(1))
                .toReviewReport(any(ReviewReportDTO.class), any(), any(SubjectReview.class));
        verifyNoMoreInteractions(modelMapper);

        // Interactions with subjectReviewService
        verify(subjectReviewService).findById(subjectReviewId);
        verifyNoMoreInteractions(subjectReviewService);

        // Interactions with reviewReportService
        verify(reviewReportService).registerReport(any(ReviewReport.class));
        verifyNoMoreInteractions(reviewReportService);
    }

    @Test
    @WithMockUser
    @DisplayName("Reporting does not work when reason is invalid")
    void reportingDoesNotWorkWhenReasonInvalid() throws Exception {
        // # Given
        ReviewReportDTO reviewReportDTO = new ReviewReportDTO("Invalid");

        long subjectReviewId = 1L;
        SubjectReview subjectReview = new SubjectReview();
        subjectReview.setId(subjectReviewId);

        // # Perform request
        mockMvc.perform(post("/api/v1/reviews/" + subjectReviewId + "/report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reviewReportDTO))
                        .with(csrf()))
                .andExpect(status().isBadRequest());

        // # Verify interactions

        // Interactions with modelMapper
        verifyNoInteractions(modelMapper);

        // Interactions with subjectReviewService
        verifyNoInteractions(subjectReviewService);

        // Interactions with reviewReportService
        verifyNoInteractions(reviewReportService);
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Report gets archived when user is ADMIN")
    void reportingGetsArchivedWhenUserIsADMIN() throws Exception {
        // # Given:
        long reportId = 1L;
        ReviewReport reviewReport = new ReviewReport("reason", new CustomUser(), new SubjectReview());

        // # Mock return value of method calls
        when(reviewReportService.findById(reportId)).thenReturn(reviewReport);

        // # Perform request
        mockMvc.perform(patch("/api/v1/reports/" + reportId + "/archive").with(csrf()))
                .andExpect(status().isOk());

        // # Verify interactions

        // Interactions with modelMapper
        verifyNoInteractions(modelMapper);

        // Interactions with subjectReviewService
        verifyNoInteractions(subjectReviewService);

        // Interactions with reviewReportService
        verify(reviewReportService).findById(reportId);
        verify(reviewReportService).archiveReport(reviewReport);
        verifyNoMoreInteractions(reviewReportService);
    }

    @Test
    @WithMockUser
    @DisplayName("Report archiving fails when USER")
    void reportArchivingFailsWhenUSER() throws Exception {
        // # Given:
        long reportId = 1L;
        ReviewReport reviewReport = new ReviewReport("reason", new CustomUser(), new SubjectReview());

        // # Mock return value of method calls
        when(reviewReportService.findById(reportId)).thenReturn(reviewReport);

        // # Perform request
        mockMvc.perform(patch("/api/v1/reports/" + reportId + "/archive").with(csrf()))
                .andExpect(status().isForbidden());

        // # Verify interactions

        // Interactions with modelMapper
        verifyNoInteractions(modelMapper);

        // Interactions with subjectReviewService
        verifyNoInteractions(subjectReviewService);

        // Interactions with reviewReportService
        verifyNoInteractions(reviewReportService);
    }

}
