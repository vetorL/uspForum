package com.example.uspForum;

import com.example.uspForum.config.SecurityConfig;
import com.example.uspForum.controller.RegistrationController;
import com.example.uspForum.service.CampusService;
import com.example.uspForum.service.CustomUserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
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

    @Nested
    class RegistrationFormValidationTests {
        private String email;
        private String username;
        private String password;
        private String campusAbbr;
        private String needsToContain;

        @BeforeEach
        void resetParameters() {
            email = "valid@usp.br";
            username = "username";
            password = "password";
            campusAbbr = "TEST";
            needsToContain = "";
        }

        @Test
        @DisplayName("Tests case where the user attempts to register an email that does not have the USP domain")
        void testNonUSPEmailRegistering() {
            email = "invalid@email.com";
            needsToContain = "O email deve ser @usp.br";
        }

        @Test
        @DisplayName("Tests case where the user attempts to register a blank email")
        void testBlankEmailRegistering() {
            email = "";
            needsToContain = "Email é obrigatório";
        }

        @Test
        @DisplayName("Tests case where the user attempts to register an email that is too short")
        void testShortEmailRegistering() {
            email = "a@a";
            needsToContain = "Email deve ter entre 6 e 254 caracteres";
        }

        @Test
        @DisplayName("Tests case where the user attempts to register an email that is too long")
        void testLongEmailRegistering() {
            email = "pY5SqFm428qtBCNfDuVy9KXiwFguhLLbdCQ4cp3S9aWU88BAeMDdgYJFTNHmbtGvxgUHUwnktb5mERyH7Y9X7fMy" +
                    "MKTZCDhF4ZiUACQxRkJcg6j2yiiJiAvnyU4y3Jdrh5MvpzLBEB7Md8NFLW6V5T0ZUEBPEDYdzEXEZCGb2UZv2c0T" +
                    "PYqNvcavbRRgrvWevpUXPekPUHK9TrrBGMUyr3jC0Z5Gu3uzHJQWHL5a3th4mWrgdK6uEQ7TifUUDpF@usp.br";
            needsToContain = "Email deve ter entre 6 e 254 caracteres";
        }

        @Test
        @DisplayName("Tests case where the user attempts to register an email that is already registered")
        void testRegisteringEmailThatIsAlreadyRegistered() {
            email = "alreadyRegistered@usp.br";
            when(customUserService.existsByEmail(email)).thenReturn(true);
            needsToContain = "Este e-mail já está cadastrado!";
        }

        @Test
        @DisplayName("Tests case where the user attempts to register a blank username")
        void testBlankUsernameRegistering() {
            username = "";
            needsToContain = "Nome de usuário é obrigatório";
        }

        @Test
        @DisplayName("Tests case where the user attempts to register a username that is too short")
        void testShortUsernameRegistering() {
            username = "a";
            needsToContain = "Nome de usuário deve ter entre 3 e 16 caracteres";
        }

        @Test
        @DisplayName("Tests case where the user attempts to register a username that is too long")
        void testLongUsernameRegistering() {
            username = "aaaaaaaaaaaaaaaaa";
            needsToContain = "Nome de usuário deve ter entre 3 e 16 caracteres";
        }

        @Test
        @DisplayName("Tests case where the user attempts to register a username that is already registered")
        void testRegisteringNameThatIsAlreadyRegistered() {
            username = "alreadyRegistered";
            when(customUserService.existsByUsername(username)).thenReturn(true);
            needsToContain = "Nome de usuário já existe!";
        }

        @Test
        @DisplayName("Tests case where the user attempts to register a blank campus")
        void testBlankCampusRegistering() {
            campusAbbr = "";
            needsToContain = "Campus é obrigatório";
        }

        @AfterEach
        void performPostRequest() throws Exception {
            mockMvc.perform(post("/registrar")
                            .param("email", email)
                            .param("username", username)
                            .param("password", password)
                            .param("campusAbbr", campusAbbr)
                            .with(csrf())
                    )
                    .andExpect(view().name("registration"))
                    .andExpect(content().string(containsString(needsToContain)));
        }
    }

    @Test
    void testPostRegisterFormWithInvalidToken() throws Exception {
        this.mockMvc.perform(post("/registrar").with(csrf().useInvalidToken()))
                .andExpect(status().isForbidden());
    }

}
