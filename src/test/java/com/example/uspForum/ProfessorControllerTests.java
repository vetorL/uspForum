package com.example.uspForum;

import com.example.uspForum.campus.Campus;
import com.example.uspForum.config.SecurityConfig;
import com.example.uspForum.customUser.CustomUserService;
import com.example.uspForum.professor.Professor;
import com.example.uspForum.professor.ProfessorController;
import com.example.uspForum.professor.ProfessorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ProfessorController.class)
@Import(SecurityConfig.class)
public class ProfessorControllerTests {

    @MockBean
    private ProfessorService professorService;

    @MockBean
    private CustomUserService customUserService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET request is successful")
    void readIsSuccessful() throws Exception {

        Campus campus = new Campus("testing institution", "TEST");
        Professor professor = new Professor("Tested", "tested", "testor@usp.br", campus);

        String testURL = "/arquivo/" + campus.getAbbreviation() + "/docentes/" + professor.getNormalizedName();

        when(professorService.findByCampusAbbreviationAndNormalizedName(campus.getAbbreviation(),
                professor.getNormalizedName()
        )).thenReturn(professor);

        this.mockMvc.perform(get(testURL))
                .andExpect(status().isOk())
                .andExpect(view().name("professor"));

    }

}
