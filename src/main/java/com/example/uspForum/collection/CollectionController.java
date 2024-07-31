package com.example.uspForum.collection;

import com.example.uspForum.subject.Subject;
import com.example.uspForum.subject.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/arquivo/{campus}/{course}/{subject}/{professor}/acervo")
public class CollectionController {

    private final SubjectService subjectService;

    private CollectionController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public String read(@PathVariable("campus") String campusAbbreviation,
                       @PathVariable("course") String courseNormalizedName,
                       @PathVariable("subject") String subjectAbbreviation,
                       @PathVariable("professor") String professorNormalizedName,
                       Model model) {

        Subject subject = subjectService.findByCourseAndCampusAndSubjectAndProfessor(
                courseNormalizedName, campusAbbreviation, subjectAbbreviation, professorNormalizedName);

        model.addAttribute("subject", subject);
        model.addAttribute("title", "Acervo " + subjectAbbreviation + " - " +
                subject.getProfessor().getName());

        return "collection";
    }

}
