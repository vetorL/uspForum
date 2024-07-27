package com.example.uspForum.course;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/arquivo/{campusAbbr}/{courseNN}")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String read(@PathVariable("campusAbbr") String campusAbbreviation,
                            @PathVariable("courseNN") String courseNormalizedName, Model model) {
        Course course =
                courseService.findByNormalizedNameAndCampusAbbreviation(courseNormalizedName, campusAbbreviation);

        model.addAttribute("course", course);
        model.addAttribute("title", campusAbbreviation + " - " + course.getName());
        return "course";
    }

}
