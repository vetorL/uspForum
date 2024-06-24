package com.example.uspForum;

import com.example.uspForum.model.Campus;
import com.example.uspForum.model.Course;
import com.example.uspForum.model.Professor;
import com.example.uspForum.model.Subject;
import com.example.uspForum.service.SubjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class SubjectControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectService subjectService;

    @Test
    void testGetSubjectById() throws Exception {
        long subjectId = 1L;

        Course course = new Course("Testing", "testing", new Campus("TST", "Testing Test"));

        Subject subject = new Subject("Test Subject", "TS", "TCH4839", course,
                new Professor("Testor", "testor@usp.br"));
        Optional<Subject> subjectOptional = Optional.of(subject);

        when(subjectService.findSubjectById(subjectId)).thenReturn(subjectOptional);

        this.mockMvc.perform(get("/disciplina/" + subjectId))
                .andExpect(status().isOk())
                .andExpect(view().name("subject.html"))
                .andExpect(content().string(containsString("Test Subject")));
    }

}
