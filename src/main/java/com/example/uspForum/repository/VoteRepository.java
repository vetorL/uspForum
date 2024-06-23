package com.example.uspForum.repository;

import com.example.uspForum.model.CustomUser;
import com.example.uspForum.model.SubjectReview;
import com.example.uspForum.model.Vote;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Long> {

    boolean existsByVoterAndSubjectReview(CustomUser voter, SubjectReview subjectReview);

    void deleteByVoterAndSubjectReview(CustomUser voter, SubjectReview subjectReview);

    Vote findByVoterAndSubjectReview(CustomUser voter, SubjectReview subjectReview);

}
