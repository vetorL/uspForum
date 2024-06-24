package com.example.uspForum;

import com.example.uspForum.model.Campus;
import com.example.uspForum.model.Course;
import com.example.uspForum.service.CampusService;
import com.example.uspForum.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class ArchiveControllerUnitTests {

    @MockBean
    private CampusService campusService;

    @MockBean
    private CourseService courseService;

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

}
