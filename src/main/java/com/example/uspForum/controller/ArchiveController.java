package com.example.uspForum.controller;

import com.example.uspForum.course.Course;
import com.example.uspForum.subject.Subject;
import com.example.uspForum.subjectReview.SubjectReviewDTO;
import com.example.uspForum.vote.VoteDTO;
import com.example.uspForum.campus.CampusService;
import com.example.uspForum.course.CourseService;
import com.example.uspForum.professor.ProfessorService;
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

    private final CampusService campusService;
    private final CourseService courseService;
    private final SubjectService subjectService;
    private final ProfessorService professorService;

    public ArchiveController(CampusService campusService, CourseService courseService, SubjectService subjectService, ProfessorService professorService) {
        this.campusService = campusService;
        this.courseService = courseService;
        this.subjectService = subjectService;
        this.professorService = professorService;
    }

    @GetMapping("/{campus}")
    public String getCampus(@PathVariable(value = "campus") String campusAbbreviation, Model model) {
        model.addAttribute("campus", campusService.findByAbbreviation(campusAbbreviation));
        return "campus.html";
    }

    @GetMapping("/{campus}/{course}")
    public String getCourse(@PathVariable("campus") String campusAbbreviation,
                            @PathVariable("course") String courseNormalizedName, Model model) {
        Course course =
                courseService.findByNormalizedNameAndCampusAbbreviation(courseNormalizedName, campusAbbreviation);

        model.addAttribute("course", course);
        model.addAttribute("title", campusAbbreviation + " - " + course.getName());
        return "course.html";
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

    @GetMapping("/{campus}/{course}/{subject}/{professor}")
    public String getSubject(@PathVariable("campus") String campusAbbreviation,
                             @PathVariable("course") String courseNormalizedName,
                             @PathVariable("subject") String subjectAbbreviation,
                             @PathVariable("professor") String professorNormalizedName,
                             Model model) {
        Subject subject = subjectService.findByCourseAndCampusAndSubjectAndProfessor(
                courseNormalizedName, campusAbbreviation, subjectAbbreviation, professorNormalizedName);
        model.addAttribute("subject", subject);
        model.addAttribute("subjectReviewDTO", new SubjectReviewDTO());
        model.addAttribute("voteDTO", new VoteDTO());
        model.addAttribute("title",
                subjectAbbreviation + " - " + subject.getProfessor().getName());
        return "subject.html";
    }

    @GetMapping("/{campus}/docentes/{professor}")
    public String getCampusProfessor(@PathVariable("campus") String campusAbbreviation,
                             @PathVariable("professor") String professorNormalizedName,
                             Model model) {
        model.addAttribute("professor", professorService.findByCampusAbbreviationAndNormalizedName(
                campusAbbreviation, professorNormalizedName
        ));
        return "professor.html";
    }

}
