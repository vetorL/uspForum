package com.example.uspForum;

import com.example.uspForum.model.CustomUser;
import com.example.uspForum.model.RegistrationFormDTO;
import com.example.uspForum.repository.CustomUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

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
    void testPostRegisterForm() throws Exception {

        RegistrationFormDTO form = new RegistrationFormDTO();

        when(customUserRepository.save(form.toCustomUser(passwordEncoder))).thenReturn(new CustomUser());

        this.mockMvc.perform(post("/registrar").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void testPostRegisterFormWithInvalidToken() throws Exception {

        RegistrationFormDTO form = new RegistrationFormDTO();

        when(customUserRepository.save(form.toCustomUser(passwordEncoder))).thenReturn(new CustomUser());

        this.mockMvc.perform(post("/registrar").with(csrf().useInvalidToken()))
                .andExpect(status().isForbidden());
    }

}
