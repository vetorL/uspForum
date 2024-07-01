package com.example.uspForum;

import com.example.uspForum.model.Campus;
import com.example.uspForum.model.Course;
import com.example.uspForum.model.Professor;
import com.example.uspForum.model.Subject;
import com.example.uspForum.service.CampusService;
import com.example.uspForum.service.CourseService;
import com.example.uspForum.service.ProfessorService;
import com.example.uspForum.service.SubjectService;
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
    private CampusService campusService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private SubjectService subjectService;

    @MockBean
    private ProfessorService professorService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCampusTest() throws Exception {

        Campus campus = new Campus("tesing institution", "TEST");

        when(campusService.findByAbbreviation("TEST")).thenReturn(campus);

        this.mockMvc.perform(get("/arquivo/" + campus.getAbbreviation()))
                .andExpect(status().isOk())
                .andExpect(view().name("campus.html"));
    }

    @Test
    public void getCourseTest() throws Exception {
        Campus campus = new Campus("tesing institution", "TEST");
        Course course = new Course("test name", "test-name", campus);

        String testURL = "/arquivo/" + campus.getAbbreviation() + "/" + course.getNormalizedName();

        when(courseService.findByNormalizedNameAndCampusAbbreviation(
                course.getNormalizedName(), campus.getAbbreviation())).thenReturn(course);

        this.mockMvc.perform(get(testURL))
                .andExpect(status().isOk())
                .andExpect(view().name("course.html"));
    }

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

    @Test
    void getCampusProfessorTest() throws Exception {
        Campus campus = new Campus("testing institution", "TEST");
        Professor professor = new Professor("Testor Tested", "testor-tested", "testor@usp.br", campus);

        String testURL = "/arquivo/" + campus.getAbbreviation() + "/docentes/" + professor.getNormalizedName();

        when(professorService.findByCampusAbbreviationAndNormalizedName(campus.getAbbreviation(),
                professor.getNormalizedName()
        )).thenReturn(professor);

        this.mockMvc.perform(get(testURL))
                .andExpect(status().isOk())
                .andExpect(view().name("professor.html"));
    }

}
