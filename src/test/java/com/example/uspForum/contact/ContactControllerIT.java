package com.example.uspForum.contact;

import com.example.uspForum.UspForumApplication;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {UspForumApplication.class})
@WebAppConfiguration
public class ContactControllerIT {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.webApplicationContext)
                .build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesSubjectReviewController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertInstanceOf(MockServletContext.class, servletContext);
        assertNotNull(webApplicationContext.getBean(ContactController.class));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    @DisplayName("Valid Contact POST is successful when authenticated")
    void validContactPostIsSuccessfulWhenAuthenticated() throws Exception {
        // # Check that there are no Contacts in the DB
        assertEquals(0, contactRepository.count());

        // # Make POST request to create Contact

        // NOTE: this test class is not configured with SecurityConfig, thus it does not block the request at the
        // controller level. What happens is that when the controller delegates to the service layer the
        // @PreAuthorize will decide whether to accept the request or not
        mockMvc.perform(post("/contato")
                        .param("subjectMatter", "Reportar Bug")
                        .param("content", "content")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("contact"));

        // # Check that Contact is in the DB
        assertEquals(1, contactRepository.count());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    @DisplayName("Invalid subject Contact POST is unsuccessful")
    void invalidSubjectContactPostIsUnsuccessful() throws Exception {
        // # Check that there are no Contacts in the DB
        assertEquals(0, contactRepository.count());

        // # Make POST request to create Contact

        // NOTE: this test class is not configured with SecurityConfig, thus it does not block the request at the
        // controller level. What happens is that when the controller delegates to the service layer the
        // @PreAuthorize will decide whether to accept the request or not
        mockMvc.perform(post("/contato")
                        .param("subjectMatter", "Invalid")
                        .param("content", "content")
                        .with(csrf()))
                .andExpect(status().isBadRequest());

        // # Check that Contact is NOT in the DB
        assertEquals(0, contactRepository.count());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    @DisplayName("Invalid content Contact POST is unsuccessful")
    void invalidContentContactPostIsUnsuccessful() throws Exception {
        // # Check that there are no Contacts in the DB
        assertEquals(0, contactRepository.count());

        // # Make POST request to create Contact

        // NOTE: this test class is not configured with SecurityConfig, thus it does not block the request at the
        // controller level. What happens is that when the controller delegates to the service layer the
        // @PreAuthorize will decide whether to accept the request or not
        mockMvc.perform(post("/contato")
                        .param("subjectMatter", "Reportar Bug")
                        .param("content", "")
                        .with(csrf()))
                .andExpect(status().isBadRequest());

        // # Check that Contact is NOT in the DB
        assertEquals(0, contactRepository.count());
    }

    @Test
    @DirtiesContext
    @WithAnonymousUser
    @DisplayName("Valid Contact POST is unsuccessful when not authenticated")
    void validContactPostIsUnsuccessfulWhenUnauthenticated() throws Exception {
        // # Check that there are no Contacts in the DB
        assertEquals(0, contactRepository.count());

        // # Make POST request to create Contact

        // NOTE: this test class is not configured with SecurityConfig, thus it does not block the request at the
        // controller level. What happens is that when the controller delegates to the service layer the
        // @PreAuthorize will decide whether to accept the request or not
        try {
            mockMvc.perform(post("/contato")
                            .param("subjectMatter", "Reportar Bug")
                            .param("content", "content")
                            .with(csrf()))
                    .andExpect(status().is3xxRedirection());
        } catch (Exception e) {
            assertInstanceOf(AccessDeniedException.class, e.getCause());
        }

        // # Check that Contact is NOT in the DB
        assertEquals(0, contactRepository.count());
    }


}
