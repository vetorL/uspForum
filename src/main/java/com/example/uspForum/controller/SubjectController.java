package com.example.uspForum.controller;

import com.example.uspForum.model.CustomUser;
import com.example.uspForum.model.Subject;
import com.example.uspForum.model.SubjectReview;
import com.example.uspForum.model.SubjectReviewDTO;
import com.example.uspForum.service.SubjectService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/disciplina")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("{id}")
    public String getSubjectById(@PathVariable("id") Long id, Model model) {
        Optional<Subject> subject = subjectService.findSubjectById(id);

        if(subject.isPresent()) {
            model.addAttribute("subject", subject.get());
            model.addAttribute("subjectReviewDTO", new SubjectReviewDTO());
        } else {
            return "redirect:/";
        }

        return "subject.html";
    }

    @PostMapping("/postar/{id}")
    public String postSubjectReview(@PathVariable("id") Long id,
                                    @ModelAttribute SubjectReviewDTO subjectReviewDTO,
                                    @AuthenticationPrincipal CustomUser author) {

        Optional<Subject> subject = subjectService.findSubjectById(id);

        if(subject.isPresent()) {
            if(subjectService.userAlreadyPostedReview(author, subject.get())) {
                // blocks posting again
                return "redirect:/disciplina/" + subject.get().getId();
            }

            subjectService.postSubjectReview(
                    subjectReviewDTO.toSubjectReview(author, subject.get())
            );
            return "redirect:/disciplina/" + subject.get().getId();
        } else {
            return "redirect:/";
        }

    }

}
