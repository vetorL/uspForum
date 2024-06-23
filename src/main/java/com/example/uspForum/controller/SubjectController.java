package com.example.uspForum.controller;

import com.example.uspForum.model.*;
import com.example.uspForum.service.SubjectReviewService;
import com.example.uspForum.service.SubjectService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/disciplina")
public class SubjectController {

    private final SubjectService subjectService;
    private final SubjectReviewService subjectReviewService;

    public SubjectController(SubjectService subjectService, SubjectReviewService subjectReviewService) {
        this.subjectService = subjectService;
        this.subjectReviewService = subjectReviewService;
    }

    @GetMapping("{id}")
    public String getSubjectById(@PathVariable("id") Long id, Model model) {
        Optional<Subject> subject = subjectService.findSubjectById(id);

        if(subject.isPresent()) {
            model.addAttribute("subject", subject.get());
            model.addAttribute("subjectReviewDTO", new SubjectReviewDTO());
            model.addAttribute("voteDTO", new VoteDTO());
        } else {
            return "redirect:/";
        }

        return "subject.html";
    }

    @PostMapping("/votar")
    @Transactional
    public String postSubjectReviewVote(@RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer,
                                      @ModelAttribute  VoteDTO voteDTO,
                                      @AuthenticationPrincipal CustomUser voter) {

        SubjectReview subjectReview = subjectReviewService.findById(voteDTO.getSubjectReviewId());

        Vote vote = voteDTO.toVote(voter, subjectReview);

        // if user already voted remove vote from database
        if(subjectReviewService.userAlreadyVotedOnReview(voter, subjectReview)) {
            // identify vote type
            int value = subjectReviewService.getUserReviewVoteType(voter, subjectReview);

            // if of the same type remove it from database
            if(value == vote.getVote()) {
                subjectReviewService.removeVoteFromReview(voter, subjectReview);
            }

            // if not of the same type then update it
            else if (value != vote.getVote()) {
                subjectReviewService.removeVoteFromReview(voter, subjectReview);
                subjectReviewService.addVoteToReview(vote);
            }

        } else {
            // else add vote to database
            subjectReviewService.addVoteToReview(vote);
        }

        return "redirect:" + referrer;

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
