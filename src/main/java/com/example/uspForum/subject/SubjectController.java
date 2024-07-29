package com.example.uspForum.subject;

import com.example.uspForum.subjectReview.SubjectReview;
import com.example.uspForum.subjectReview.SubjectReviewDTO;
import com.example.uspForum.vote.VoteDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/arquivo/{campusAbbr}/{courseNN}/{subjectNN}")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/{professorNN}")
    public String read(@PathVariable("campusAbbr") String campusAbbreviation,
                       @PathVariable("courseNN") String courseNormalizedName,
                       @PathVariable("subjectNN") String subjectAbbreviation,
                       @PathVariable("professorNN") String professorNormalizedName,
                       @RequestParam(required = false, defaultValue = "") String sort,
                       Model model) {

        Subject subject = subjectService.findByCourseAndCampusAndSubjectAndProfessor(
                courseNormalizedName, campusAbbreviation, subjectAbbreviation, professorNormalizedName);

        List<SubjectReview> sortedReviews;

        if (sort.equals("recentes")) {
            sortedReviews = subject.getReviews().stream()
                    .sorted(Comparator.comparing(SubjectReview::getCreatedAt).reversed())
                    .toList();
        } else {
            sortedReviews = subject.getReviews().stream()
                    .sorted(Comparator.comparing(SubjectReview::getTotalVotes).reversed())
                    .toList();
        }

        model.addAttribute("subject", subject);
        model.addAttribute("sortedReviews", sortedReviews);
        model.addAttribute("sort", sort);
        model.addAttribute("subjectReviewDTO", new SubjectReviewDTO());
        model.addAttribute("voteDTO", new VoteDTO());
        model.addAttribute("title",
                subjectAbbreviation + " - " + subject.getProfessor().getName());

        return "subject";
    }

    @GetMapping
    public String readSubjectProfessorList(@PathVariable("campusAbbr") String campusAbbreviation,
                                           @PathVariable("courseNN") String courseNormalizedName,
                                           @PathVariable("subjectNN") String subjectAbbreviation,
                                           Model model) {

        List<Subject> subjects = subjectService.findByCourseNormalizedNameAndCourseCampusAbbreviationAndAbbreviation(
                courseNormalizedName, campusAbbreviation, subjectAbbreviation);

        model.addAttribute("subjects", subjects);
        model.addAttribute("title", subjectAbbreviation);
        model.addAttribute("sampleSubject", subjects.get(0));

        return "subject-professor-list";
    }

}
