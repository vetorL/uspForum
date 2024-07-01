package com.example.uspForum;

import com.example.uspForum.controller.VoteController;
import com.example.uspForum.model.VoteDTO;
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

}
