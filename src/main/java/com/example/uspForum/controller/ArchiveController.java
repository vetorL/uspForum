package com.example.uspForum.controller;

import com.example.uspForum.service.CampusService;
import com.example.uspForum.service.CourseService;
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

    public ArchiveController(CampusService campusService, CourseService courseService) {
        this.campusService = campusService;
        this.courseService = courseService;
    }

    @GetMapping("/{campus}")
    public String getCampus(@PathVariable(value = "campus") String campusAbbreviation, Model model) {
        model.addAttribute("campus", campusService.findByAbbreviation(campusAbbreviation));
        return "campus.html";
    }

}
