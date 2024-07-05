package com.example.uspForum;

import com.example.uspForum.config.SecurityConfig;
import com.example.uspForum.controller.ProfileController;
import com.example.uspForum.model.Campus;
import com.example.uspForum.model.CustomUser;
import com.example.uspForum.service.CustomUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ProfileController.class)
@Import(SecurityConfig.class)
public class ProfileControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomUserService customUserService;

    @Test
    @DisplayName("Tests GET request for a user's profile page while unauthenticated")
    void testGetProfile() throws Exception {
        when(customUserService.findByUsername(anyString()))
                .thenReturn(new CustomUser("a@a", "test", "a", new Campus()));

        mockMvc.perform(get("/perfil/{username}", "test"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"));
    }

}
