package com.example.uspForum;

import com.example.uspForum.model.Campus;
import com.example.uspForum.model.CustomUser;
import com.example.uspForum.model.RegistrationFormDTO;
import com.example.uspForum.repository.CustomUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationControllerUnitTests {

    @MockBean
    private CustomUserRepository customUserRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

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
    void testPostRegisterFormWithInvalidToken() throws Exception {

        RegistrationFormDTO form = new RegistrationFormDTO();

        when(customUserRepository.save(form.toCustomUser(passwordEncoder, new Campus()))).thenReturn(new CustomUser());

        this.mockMvc.perform(post("/registrar").with(csrf().useInvalidToken()))
                .andExpect(status().isForbidden());
    }

}
