package com.example.uspForum.administration;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdministrationController.class)
@Import(SecurityConfig.class)
public class AdministrationControllerTests {

    @MockBean
    private CustomUserService customUserService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("GET request by admin for index is successful")
    void getIndexByAdminRequestIsSuccessful() throws Exception {

        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("administration"));

    }

    @Test
    @WithMockUser
    @DisplayName("GET request by random user for index is forbidden")
    void getIndexByUserRequestIsUnsuccessful() throws Exception {

        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithAnonymousUser
    @DisplayName("GET request by anonymous user for index is unauthorized")
    void getIndexByAnonymousUserRequestIsUnsuccessful() throws Exception {

        mockMvc.perform(get("/admin"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

    }

    // ### Tests for /admin/reviews ###

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("GET request by admin for Reviews is successful")
    void getReviewsByAdminRequestIsSuccessful() throws Exception {

        mockMvc.perform(get("/admin/reviews"))
                .andExpect(status().isOk())
                .andExpect(view().name("administration"));

    }

    @Test
    @WithMockUser
    @DisplayName("GET request by random user for Reviews is forbidden")
    void getReviewsByUserRequestIsUnsuccessful() throws Exception {

        mockMvc.perform(get("/admin/reviews"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithAnonymousUser
    @DisplayName("GET request by anonymous user for Reviews is unauthorized")
    void getReviewsByAnonymousUserRequestIsUnsuccessful() throws Exception {

        mockMvc.perform(get("/admin/reviews"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

    }

    // ### Tests for /admin/contacts ###

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("GET request by admin for Contacts is successful")
    void getContactsByAdminRequestIsSuccessful() throws Exception {

        mockMvc.perform(get("/admin/contatos"))
                .andExpect(status().isOk())
                .andExpect(view().name("administration"));

    }

    @Test
    @WithMockUser
    @DisplayName("GET request by random user for Contacts is forbidden")
    void getContactsByUserRequestIsUnsuccessful() throws Exception {

        mockMvc.perform(get("/admin/contatos"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithAnonymousUser
    @DisplayName("GET request by anonymous user for Contacts is unauthorized")
    void getContactsByAnonymousUserRequestIsUnsuccessful() throws Exception {

        mockMvc.perform(get("/admin/contatos"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

    }

}
