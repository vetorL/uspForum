package com.example.uspForum;

import com.example.uspForum.campus.Campus;
import com.example.uspForum.course.Course;
import com.example.uspForum.professor.Professor;
import com.example.uspForum.subject.Subject;
import com.example.uspForum.campus.CampusService;
import com.example.uspForum.course.CourseService;
import com.example.uspForum.professor.ProfessorService;
import com.example.uspForum.subject.SubjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ArchiveControllerUnitTests {

    @MockBean
    private SubjectService subjectService;

    @MockBean
    private ProfessorService professorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getSubjectProfessorListTest() throws Exception {
        Campus campus = new Campus("testing institution", "TEST");
        Course course = new Course("test name", "test-name", campus);
        Subject subject = new Subject("testing", "TST", "T438902", course, new Professor());

        String testURL = "/arquivo/" + campus.getAbbreviation() + "/"
                + course.getNormalizedName() + "/" + subject.getAbbreviation();

        when(subjectService.findByCourseNormalizedNameAndCourseCampusAbbreviationAndAbbreviation(
                course.getNormalizedName(), campus.getAbbreviation(), subject.getAbbreviation()))
                .thenReturn(List.of(subject));

        this.mockMvc.perform(get(testURL))
                .andExpect(status().isOk())
                .andExpect(view().name("subject-professor-list.html"));
    }

    @Test
    void getSubjectTest() throws Exception {
        Campus campus = new Campus("testing institution", "TEST");
        Course course = new Course("test name", "test-name", campus);
        Professor professor = new Professor("Testor Tested", "testor-tested", "testor@usp.br", campus);
        Subject subject = new Subject("testing", "TST", "T438902", course, professor);

        String testURL = "/arquivo/" + campus.getAbbreviation() + "/"
                + course.getNormalizedName() + "/" + subject.getAbbreviation() + "/" + professor.getNormalizedName();

        when(subjectService.findByCourseAndCampusAndSubjectAndProfessor(
                course.getNormalizedName(), campus.getAbbreviation(), subject.getAbbreviation(),
                professor.getNormalizedName()
        )).thenReturn(subject);

        this.mockMvc.perform(get(testURL))
                .andExpect(status().isOk())
                .andExpect(view().name("subject.html"));
    }

}
