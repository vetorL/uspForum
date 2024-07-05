package com.example.uspForum;

import com.example.uspForum.model.Campus;
import com.example.uspForum.service.CampusService;
import com.example.uspForum.service.CourseService;
import com.example.uspForum.service.ProfessorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class CreationControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampusService campusService;

    @MockBean
    private ProfessorService professorService;

    @MockBean
    private CourseService courseService;

    @WithUserDetails("test")
    @Test
    void testGetCreateSubjectWithAuthority() throws Exception {

        when(campusService.findAll()).thenReturn(new ArrayList<>());
        when(professorService.findAll()).thenReturn(new ArrayList<>());
        when(courseService.getAllDistinctCourseNames()).thenReturn(new ArrayList<>());

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
