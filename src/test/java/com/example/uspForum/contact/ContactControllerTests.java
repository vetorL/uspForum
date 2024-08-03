package com.example.uspForum.contact;

import com.example.uspForum.config.SecurityConfig;
import com.example.uspForum.customUser.CustomUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContactController.class)
@Import(SecurityConfig.class)
public class ContactControllerTests {

    @MockBean
    private ContactService contactService;

    @MockBean
    private CustomUserService customUserService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    @DisplayName("GET is successful when authenticated")
    void readIsSuccessfulWhenAuthenticated() throws Exception {

        mockMvc.perform(get("/contato"))
                .andExpect(status().isOk())
                .andExpect(view().name("contact"));

    }

    @Test
    @WithAnonymousUser
    @DisplayName("GET is unsuccessful when not authenticated")
    void readIsUnsuccessfulWhenNotAuthenticated() throws Exception {

        mockMvc.perform(get("/contato"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

    }

    @Test
    @WithMockUser
    @DisplayName("POST request is successful when authenticated")
    void postIsSuccessfulWhenAuthenticated() throws Exception {

        mockMvc.perform(post("/contato")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("contact"));

    }

    @Test
    @WithMockUser
    @DisplayName("POST request is unsuccessful when invalid csrf")
    void postIsUnsuccessfulWhenInvalidCsrf() throws Exception {

        mockMvc.perform(post("/contato")
                        .with(csrf().useInvalidToken()))
                .andExpect(status().isForbidden());

    }

}
