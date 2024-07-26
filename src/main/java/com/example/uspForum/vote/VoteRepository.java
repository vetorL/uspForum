package com.example.uspForum.vote;

import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.subjectReview.SubjectReview;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Long> {

    boolean existsByVoterAndSubjectReview(CustomUser voter, SubjectReview subjectReview);

    void deleteByVoterAndSubjectReview(CustomUser voter, SubjectReview subjectReview);

    Vote findByVoterAndSubjectReview(CustomUser voter, SubjectReview subjectReview);

}
