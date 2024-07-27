package com.example.uspForum.controller;

import com.example.uspForum.subject.Subject;
import com.example.uspForum.subject.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/arquivo")
public class ArchiveController {

    private final SubjectService subjectService;

    public ArchiveController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/{campus}/{course}/{subject}")
    public String getSubjectProfessorList(@PathVariable("campus") String campusAbbreviation,
                             @PathVariable("course") String courseNormalizedName,
                             @PathVariable("subject") String subjectAbbreviation,
                             Model model) {
        List<Subject> subjects = subjectService.findByCourseNormalizedNameAndCourseCampusAbbreviationAndAbbreviation(
                courseNormalizedName, campusAbbreviation, subjectAbbreviation);
        model.addAttribute("subjects", subjects);
        model.addAttribute("title", subjectAbbreviation);
        model.addAttribute("sampleSubject", subjects.get(0));
        return "subject-professor-list.html";
    }

}
