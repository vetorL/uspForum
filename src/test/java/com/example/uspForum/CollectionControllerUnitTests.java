package com.example.uspForum;

import com.example.uspForum.config.SecurityConfig;
import com.example.uspForum.controller.CollectionController;
import com.example.uspForum.model.Course;
import com.example.uspForum.model.Professor;
import com.example.uspForum.model.Subject;
import com.example.uspForum.service.CustomUserService;
import com.example.uspForum.service.SubjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CollectionController.class)
@Import(SecurityConfig.class)
public class CollectionControllerUnitTests {

    @MockBean
    private SubjectService subjectService;

    // Required by SecurityConfig
    @MockBean
    private CustomUserService customUserService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test that GET request is successful")
    void getIsSuccessful() throws Exception {
        Professor professor = new Professor();
        Subject subject = new Subject("name", "abbreviation", "code", new Course(), professor);

        when(subjectService.findByCourseAndCampusAndSubjectAndProfessor(anyString(), anyString(), anyString(),
                anyString())).thenReturn(subject);

        mockMvc.perform(get("/arquivo/{campus}/{course}/{subject}/{professor}/acervo", "campus",
                        "course", "subject", "professor"))
                .andExpect(view().name("collection"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("subject", equalTo(subject)));

        verify(subjectService, times(1)).findByCourseAndCampusAndSubjectAndProfessor(anyString(),
                anyString(), anyString(), anyString());
        verifyNoMoreInteractions(subjectService);
    }

}
