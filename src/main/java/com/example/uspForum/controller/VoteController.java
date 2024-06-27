package com.example.uspForum.controller;

import com.example.uspForum.model.CustomUser;
import com.example.uspForum.model.SubjectReview;
import com.example.uspForum.model.Vote;
import com.example.uspForum.model.VoteDTO;
import com.example.uspForum.service.SubjectReviewService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteController {

    private final SubjectReviewService subjectReviewService;

    public VoteController(SubjectReviewService subjectReviewService) {
        this.subjectReviewService = subjectReviewService;
    }

    @PostMapping(value = "/disciplina/votar", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public void processVote(
            @RequestBody VoteDTO voteDTO,
            @AuthenticationPrincipal CustomUser voter
            ) {
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
    }

}
