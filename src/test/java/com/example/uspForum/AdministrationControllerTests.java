package com.example.uspForum;

import com.example.uspForum.administration.AdministrationController;
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
    @DisplayName("GET request by admin for reviews tab is successful")
    void getReviewsTabByAdminRequestIsSuccessful() throws Exception {

        mockMvc.perform(get("/admin?tab=reviews"))
                .andExpect(status().isOk())
                .andExpect(view().name("administration"));

    }

    @Test
    @WithMockUser
    @DisplayName("GET request by random user for reviews tab is forbidden")
    void getReviewsTabByUserRequestIsUnsuccessful() throws Exception {

        mockMvc.perform(get("/admin?tab=reviews"))
                .andExpect(status().isForbidden());

    }

    @Test
    @WithAnonymousUser
    @DisplayName("GET request by anonymous user for reviews tab is unauthorized")
    void getReviewsTabByAnonymousUserRequestIsUnsuccessful() throws Exception {

        mockMvc.perform(get("/admin?tab=reviews"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));

    }

}
