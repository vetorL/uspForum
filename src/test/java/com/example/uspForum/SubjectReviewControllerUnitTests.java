package com.example.uspForum;

import com.example.uspForum.config.SecurityConfig;
import com.example.uspForum.controller.SubjectReviewController;
import com.example.uspForum.exception.NotFoundException;
import com.example.uspForum.model.*;
import com.example.uspForum.service.CustomUserService;
import com.example.uspForum.service.SubjectReviewService;
import com.example.uspForum.service.SubjectService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubjectReviewController.class)
@Import(SecurityConfig.class)
public class SubjectReviewControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SubjectService subjectService;

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
                .andExpect(status().isNoContent());

        verify(subjectReviewService).delete(any(SubjectReview.class));
    }

    @Test
    @WithMockUser
    @DisplayName("Test deleting when user authenticated, but review not found")
    void testDeletingWhenAuthenticatedButReviewNotFound() throws Exception {

        long subjectReviewId = 1L;

        when(subjectReviewService.findById(subjectReviewId)).thenThrow(NotFoundException.class);

        mockMvc.perform(delete("/api/v1/reviews/" + subjectReviewId).with(csrf()))
                .andExpect(status().isNotFound());

        verify(subjectReviewService, never()).delete(any());
    }

    @Test
    @DisplayName("Test editing when user unauthenticated")
    void testEditingWhenUserUnauthenticated() throws Exception {

        long subjectReviewId = 1L;

        mockMvc.perform(put("/api/v1/reviews/" + subjectReviewId).with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

        verifyNoInteractions(subjectReviewService);
    }

    @Test
    @WithMockUser
    @DisplayName("Test editing when user authenticated, but wrong csrf")
    void testEditingWhenNoCsrf() throws Exception {

        long subjectReviewId = 1L;

        mockMvc.perform(put("/api/v1/reviews/" + subjectReviewId).with(csrf().useInvalidToken()))
                .andExpect(status().isForbidden());

        verifyNoInteractions(subjectReviewService);
    }

    @Test
    @WithMockUser
    @DisplayName("Test editing when user authenticated (happy flow)")
    void testEditingWhenAuthenticatedHappyFlow() throws Exception {
        long subjectReviewId = 1L;
        SubjectReviewDTO subjectReviewDTO = new SubjectReviewDTO("title", "content",
                "Neutro");

        when(subjectReviewService.findById(subjectReviewId))
                .thenReturn(new SubjectReview());
        when(subjectReviewService.deleteAndCreate(any(SubjectReview.class), any(SubjectReview.class)))
                .thenReturn(new SubjectReviewResponse());

        mockMvc.perform(put("/api/v1/reviews/" + subjectReviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectReviewDTO))
                        .with(csrf())
                )
                .andExpect(status().isCreated());

        verify(subjectReviewService).findById(anyLong());
        verify(subjectReviewService).deleteAndCreate(any(SubjectReview.class), any(SubjectReview.class));
        verifyNoMoreInteractions(subjectReviewService);
    }

    @Test
    @WithMockUser
    @DisplayName("Test editing when user authenticated, but review not found")
    void testEditingWhenAuthenticatedButReviewNotFound() throws Exception {
        long subjectReviewId = 1L;
        SubjectReviewDTO subjectReviewDTO = new SubjectReviewDTO("title", "content",
                "Neutro");

        when(subjectReviewService.findById(subjectReviewId)).thenThrow(NotFoundException.class);

        mockMvc.perform(put("/api/v1/reviews/" + subjectReviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectReviewDTO))
                        .with(csrf())
                )
                .andExpect(status().isNotFound());

        verify(subjectReviewService, times(1)).findById(anyLong());
        verifyNoMoreInteractions(subjectReviewService);
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Test creating when user unauthenticated")
    void createWhenUserUnauthenticatedFails() throws Exception {
        long associatedSubjectId = 1L;
        SubjectReviewDTO subjectReviewDTO = new SubjectReviewDTO("title", "content",
                "Neutro");

        mockMvc.perform(post("/api/v1/subject/" + associatedSubjectId + "/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectReviewDTO))
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

        verifyNoInteractions(subjectReviewService);
    }

    @Test
    @WithMockUser
    @DisplayName("Test creating when user authenticated, but invalid csrf")
    void creatingWhenInvalidCsrfFails() throws Exception {
        long subjectReviewId = 1L;
        SubjectReviewDTO subjectReviewDTO = new SubjectReviewDTO("title", "content",
                "Neutro");

        mockMvc.perform(post("/api/v1/reviews/" + subjectReviewId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectReviewDTO))
                        .with(csrf().useInvalidToken()))
                .andExpect(status().isForbidden());

        verifyNoInteractions(subjectReviewService);
    }

}
