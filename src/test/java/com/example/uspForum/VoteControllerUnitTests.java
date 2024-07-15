package com.example.uspForum;

import com.example.uspForum.controller.VoteController;
import com.example.uspForum.model.*;
import com.example.uspForum.service.SubjectReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VoteController.class)
public class VoteControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectReviewService subjectReviewService;

    @Test
    @WithMockUser("test")
    @DisplayName("Tests voting when the user is authenticated.")
    void testVotingAuthenticated() throws Exception {
        VoteDTO voteDTO = new VoteDTO(1, "up");

        // a SubjectReview instance needs to be returned so that subjectReview.getAuthor() may be called upon it
        // and a CustomUser instance needs to be returned so that setRep and getRep may be called upon it
        when(subjectReviewService.findById(voteDTO.getSubjectReviewId())).thenReturn(new SubjectReview(new CustomUser(),
                new Subject(), "title", "content", "recommendation"));

        mockMvc.perform(post("/disciplina/votar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(voteDTO))
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Tests voting when unauthenticated.")
    void testVotingUnauthenticated() throws Exception {
        VoteDTO voteDTO = new VoteDTO(1, "up");

        mockMvc.perform(post("/disciplina/votar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(voteDTO))
                        .with(csrf()))
                        .andExpect(status().isUnauthorized());
    }

    public static String toJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @WithMockUser
    @DisplayName("Test voting up when the user has yet to vote")
    void testVoteUpWhenUserHasYetToVote() throws Exception {
        // Given
        long subjectReviewId = 1;
        VoteDTO voteDTO = new VoteDTO(subjectReviewId, "up");
        CustomUser voter = new CustomUser();
        SubjectReview subjectReview = new SubjectReview(new CustomUser(),
                new Subject(), "title", "content", "recommendation");

        // Mocking behavior of findById
        when(subjectReviewService.findById(voteDTO.getSubjectReviewId())).thenReturn(subjectReview);

        // Mocking behavior of userAlreadyVotedOnReview (makes it return false)
        when(subjectReviewService.userAlreadyVotedOnReview(voter, subjectReview))
                .thenReturn(false);

        // Make POST request
        mockMvc.perform(post("/disciplina/votar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(voteDTO))
                        .with(csrf()))
                .andExpect(status().isOk());

        // ### VERIFICATIONS ###
        // The verifications must come after the POST request

        // Verify that findById was called just once
        verify(subjectReviewService, times(1)).findById(anyLong());

        // Verify that userAlreadyVotedOnReview was called just once
        verify(subjectReviewService, times(1)).userAlreadyVotedOnReview(any(), any(SubjectReview.class));

        // Verify that the addVoteToReview method was called just once
        verify(subjectReviewService, times(1)).addVoteToReview(any(Vote.class));

        // Verify that no other method was called on subjectReviewService after addVoteToReview
        verifyNoMoreInteractions(subjectReviewService);

        // Check that the subjectReview's author now has a rep of 1
        assertEquals(1, subjectReview.getAuthor().getRep());
    }

}
