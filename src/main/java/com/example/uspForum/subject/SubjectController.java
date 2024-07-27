package com.example.uspForum.subject;

import com.example.uspForum.subjectReview.SubjectReviewDTO;
import com.example.uspForum.vote.VoteDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/arquivo/{campusAbbr}/{courseNN}/{subjectNN}/{professorNN}")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public String read(@PathVariable("campusAbbr") String campusAbbreviation,
                       @PathVariable("courseNN") String courseNormalizedName,
                       @PathVariable("subjectNN") String subjectAbbreviation,
                       @PathVariable("professorNN") String professorNormalizedName,
                       Model model) {

        Subject subject = subjectService.findByCourseAndCampusAndSubjectAndProfessor(
                courseNormalizedName, campusAbbreviation, subjectAbbreviation, professorNormalizedName);

        model.addAttribute("subject", subject);
        model.addAttribute("subjectReviewDTO", new SubjectReviewDTO());
        model.addAttribute("voteDTO", new VoteDTO());
        model.addAttribute("title",
                subjectAbbreviation + " - " + subject.getProfessor().getName());

        return "subject";
    }

}
