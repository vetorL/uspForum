package com.example.uspForum;

import com.example.uspForum.campus.Campus;
import com.example.uspForum.config.SecurityConfig;
import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.customUser.CustomUserService;
import com.example.uspForum.customUser.ProfileController;
import com.example.uspForum.exception.CustomUserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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
                .thenReturn(new CustomUser("a@a", "test", "a"));

        mockMvc.perform(get("/perfil/{username}", "test")
                        .with(user(new CustomUser())))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"));
    }

    @Test
    @DisplayName("Tests GET request for an inexistent user's profile")
    void testGetProfileInvalid() throws Exception {
        when(customUserService.findByUsername(anyString())).thenThrow(new CustomUserNotFoundException(""));

        mockMvc.perform(get("/perfil/{username}", "test"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error"));
    }

}
