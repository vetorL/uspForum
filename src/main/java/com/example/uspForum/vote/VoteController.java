package com.example.uspForum.vote;

import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.subjectReview.SubjectReview;
import com.example.uspForum.subjectReview.SubjectReviewService;
import com.example.uspForum.util.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoteController {

    private final SubjectReviewService subjectReviewService;
    private final ModelMapper modelMapper;

    public VoteController(SubjectReviewService subjectReviewService, ModelMapper modelMapper) {
        this.subjectReviewService = subjectReviewService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/disciplina/votar", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public void processVote(
            @RequestBody VoteDTO voteDTO,
            @AuthenticationPrincipal CustomUser voter
            ) {
        SubjectReview subjectReview = subjectReviewService.findById(voteDTO.getSubjectReviewId());

        Vote vote = modelMapper.toVote(voteDTO, voter, subjectReview);

        CustomUser subjectReviewAuthor = subjectReview.getAuthor();

        // if user already voted remove vote from database
        if(subjectReviewService.userAlreadyVotedOnReview(voter, subjectReview)) {
            // identify vote type
            int value = subjectReviewService.getUserReviewVoteType(voter, subjectReview);

            // if of the same type remove it from database
            if(value == vote.getVote()) {
                if(value == 1) {
                    // upvote being removed, thus decreases rep
                    subjectReviewAuthor.setRep(subjectReviewAuthor.getRep() - 1);
                } else if(value == -1) {
                    // down being removed, thus increases rep
                    subjectReviewAuthor.setRep(subjectReviewAuthor.getRep() + 1);
                }


                subjectReviewService.removeVoteFromReview(voter, subjectReview);
            }

            // if not of the same type then update it
            else if (value != vote.getVote()) {
                if(value == 1) {
                    // user had upvoted, now downvotes
                    // this decreases rep by 2
                    subjectReviewAuthor.setRep(subjectReviewAuthor.getRep() - 2);
                } else if(value == -1) {
                    // user had downvoted, now upvotes
                    // this increases rep by two
                    subjectReviewAuthor.setRep(subjectReviewAuthor.getRep() + 2);
                }

                subjectReviewService.removeVoteFromReview(voter, subjectReview);
                subjectReviewService.addVoteToReview(vote);
            }

        } else {
            // else add vote to database

            // this is an entirely new vote, thus adding its value to the rep is enough
            subjectReviewAuthor.setRep(subjectReviewAuthor.getRep() + vote.getVote());

            subjectReviewService.addVoteToReview(vote);
        }
    }

}
