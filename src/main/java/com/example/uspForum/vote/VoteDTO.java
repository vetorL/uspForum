package com.example.uspForum.vote;

import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.subjectReview.SubjectReview;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {

    public long subjectReviewId;
    private String vote;

    public Vote toVote(CustomUser voter, SubjectReview subjectReview) {
        int voteValue = 0;

        if(vote.equals("up")) {
            voteValue = 1;
        } else if (vote.equals("down")) {
            voteValue = -1;
        }

        return new Vote(
                voteValue,
                voter,
                subjectReview
        );
    }

}
