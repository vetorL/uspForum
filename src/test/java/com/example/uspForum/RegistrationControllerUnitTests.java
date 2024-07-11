package com.example.uspForum;

import com.example.uspForum.config.SecurityConfig;
import com.example.uspForum.controller.RegistrationController;
import com.example.uspForum.service.CampusService;
import com.example.uspForum.service.CustomUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc
public class RegistrationControllerUnitTests {

    @MockBean
    private CustomUserService customUserService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private CampusService campusService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetRegisterForm() throws Exception {
        this.mockMvc.perform(get("/registrar"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"));
    }

    @Test
    @DisplayName("Test successful registration (all fields are valid)")
    void testSuccessfulRegistration() throws Exception {
        String email = "valid@usp.br";
        String username = "username";
        String password = "password";
        String campusAbbr = "TEST";

        this.mockMvc.perform(post("/registrar")
                        .param("email", email)
                        .param("username", username)
                        .param("password", password)
                        .param("campusAbbr", campusAbbr)
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @DisplayName("Tests case where the user attempts to register an email that does not have the USP domain")
    void testNonUSPEmailRegistering() throws Exception {
        String email = "invalid@email.com";
        String username = "username";
        String password = "password";
        String campusAbbr = "TEST";

        this.mockMvc.perform(post("/registrar")
                    .param("email", email)
                    .param("username", username)
                    .param("password", password)
                    .param("campusAbbr", campusAbbr)
                    .with(csrf())
                )
                .andExpect(view().name("registration"))
                .andExpect(content().string(containsString("O email deve ser @usp.br")));
    }

    @Test
    @DisplayName("Tests case where the user attempts to register a blank email")
    void testBlankEmailRegistering() throws Exception {
        String email = "";
        String username = "username";
        String password = "password";
        String campusAbbr = "TEST";

        this.mockMvc.perform(post("/registrar")
                        .param("email", email)
                        .param("username", username)
                        .param("password", password)
                        .param("campusAbbr", campusAbbr)
                        .with(csrf())
                )
                .andExpect(view().name("registration"))
                .andExpect(content().string(containsString("Email é obrigatório")));
    }

    @Test
    void testPostRegisterFormWithInvalidToken() throws Exception {
        this.mockMvc.perform(post("/registrar").with(csrf().useInvalidToken()))
                .andExpect(status().isForbidden());
    }

}
