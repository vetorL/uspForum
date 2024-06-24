package com.example.uspForum.controller;

import com.example.uspForum.service.CampusService;
import com.example.uspForum.service.CourseService;
import com.example.uspForum.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/arquivo")
public class ArchiveController {

    private final CampusService campusService;
    private final CourseService courseService;
    private final SubjectService subjectService;

    public ArchiveController(CampusService campusService, CourseService courseService, SubjectService subjectService) {
        this.campusService = campusService;
        this.courseService = courseService;
        this.subjectService = subjectService;
    }

    @GetMapping("/{campus}")
    public String getCampus(@PathVariable(value = "campus") String campusAbbreviation, Model model) {
        model.addAttribute("campus", campusService.findByAbbreviation(campusAbbreviation));
        return "campus.html";
    }

    @GetMapping("/{campus}/{course}")
    public String getCourse(@PathVariable("campus") String campusAbbreviation,
                            @PathVariable("course") String courseNormalizedName, Model model) {
        model.addAttribute("course",
                courseService.findByNormalizedNameAndCampusAbbreviation(courseNormalizedName, campusAbbreviation));
        return "course.html";
    }

    @GetMapping("/{campus}/{course}/{subject}")
    public String getSubjectProfessorList(@PathVariable("campus") String campusAbbreviation,
                             @PathVariable("course") String courseNormalizedName,
                             @PathVariable("subject") String subjectAbbreviation,
                             Model model) {
        model.addAttribute("subjects",
                subjectService.findByCourseNormalizedNameAndCourseCampusAbbreviationAndAbbreviation(
                        courseNormalizedName, campusAbbreviation, subjectAbbreviation));
        return "subject-professor-list.html";
    }

}
