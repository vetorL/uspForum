package com.example.uspForum;

import com.example.uspForum.config.SecurityConfig;
import com.example.uspForum.controller.SubjectReviewController;
import com.example.uspForum.model.SubjectReview;
import com.example.uspForum.service.CustomUserService;
import com.example.uspForum.service.SubjectReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubjectReviewController.class)
@Import(SecurityConfig.class)
public class SubjectReviewControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectReviewService subjectReviewService;

    // Necessary for the SecurityConfig
    @MockBean
    private CustomUserService customUserService;

    @Test
    @DisplayName("Test deleting when user unauthenticated")
    void testDeletingWhenUserUnauthenticated() throws Exception {

        long subjectReviewId = 1L;

        mockMvc.perform(delete("/api/v1/reviews/" + subjectReviewId).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

        verifyNoInteractions(subjectReviewService);
    }

    @Test
    @WithMockUser
    @DisplayName("Test deleting when user authenticated, but wrong csrf")
    void testDeletingWhenNoCsrf() throws Exception {

        long subjectReviewId = 1L;

        mockMvc.perform(delete("/api/v1/reviews/" + subjectReviewId).with(csrf().useInvalidToken()))
                .andExpect(status().isForbidden());

        verifyNoInteractions(subjectReviewService);
    }

    @Test
    @WithMockUser
    @DisplayName("Test deleting when user authenticated (happy flow)")
    void testDeletingWhenAuthenticatedHappyFlow() throws Exception {

        long subjectReviewId = 1L;

        when(subjectReviewService.findById(subjectReviewId)).thenReturn(new SubjectReview());

        mockMvc.perform(delete("/api/v1/reviews/" + subjectReviewId).with(csrf()))
                .andExpect(status().isOk());

        verify(subjectReviewService).delete(any(SubjectReview.class));
    }

}
