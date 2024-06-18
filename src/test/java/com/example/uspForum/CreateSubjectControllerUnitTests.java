package com.example.uspForum;

import com.example.uspForum.model.Campus;
import com.example.uspForum.service.CampusService;
import com.example.uspForum.service.CourseService;
import com.example.uspForum.service.ProfessorService;
import com.example.uspForum.service.SubjectService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CreateSubjectControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampusService campusService;

    @MockBean
    private ProfessorService professorService;

    @MockBean
    private CourseService courseService;

    @WithMockUser(authorities="ROLE_USER")
    @Test
    void testGetCreateSubjectWithAuthority() throws Exception {

        when(campusService.findAll()).thenReturn(new ArrayList<>());
        when(professorService.findAll()).thenReturn(new ArrayList<>());
        when(courseService.getAllCourses()).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/criar"))
                .andExpect(status().isOk())
                .andExpect(view().name("create-subject.html"));

    }

    @Test
    void testGetCreateSubjectWithoutAuthority() throws Exception {

        when(campusService.findAll()).thenReturn(new ArrayList<Campus>());

        this.mockMvc.perform(get("/criar"))
                .andExpect(status().is3xxRedirection());

    }
}
