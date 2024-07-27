package com.example.uspForum;

import com.example.uspForum.campus.Campus;
import com.example.uspForum.config.SecurityConfig;
import com.example.uspForum.course.Course;
import com.example.uspForum.course.CourseController;
import com.example.uspForum.course.CourseService;
import com.example.uspForum.customUser.CustomUserService;
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

@WebMvcTest(CourseController.class)
@Import(SecurityConfig.class)
public class CourseControllerTests {

    @MockBean
    private CourseService courseService;

    @MockBean
    private CustomUserService customUserService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET request is succesful")
    void readIsSuccessful() throws Exception {

        Campus campus = new Campus("testing institution", "TEST");
        Course course = new Course("test name", "test-name", campus);

        String testURL = "/arquivo/" + campus.getAbbreviation() + "/" + course.getNormalizedName();

        when(courseService.findByNormalizedNameAndCampusAbbreviation(
                course.getNormalizedName(), campus.getAbbreviation())).thenReturn(course);

        this.mockMvc.perform(get(testURL))
                .andExpect(status().isOk())
                .andExpect(view().name("course"));

    }

}
