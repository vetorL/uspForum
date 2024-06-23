package com.example.uspForum.service;

import com.example.uspForum.model.CustomUser;
import com.example.uspForum.model.SubjectReview;
import com.example.uspForum.model.Vote;
import com.example.uspForum.repository.SubjectReviewRepository;
import com.example.uspForum.repository.VoteRepository;
import org.springframework.stereotype.Service;

@Service
public class SubjectReviewService {

    private final SubjectReviewRepository subjectReviewRepository;
    private final VoteRepository voteRepository;

    public SubjectReviewService(SubjectReviewRepository subjectReviewRepository, VoteRepository voteRepository) {
        this.subjectReviewRepository = subjectReviewRepository;
        this.voteRepository = voteRepository;
    }

    public SubjectReview findById(long id) {
        return subjectReviewRepository.findById(id).orElse(null);
    }

    public void addVoteToReview(Vote vote) {
        voteRepository.save(vote);
    }

    public void removeVoteFromReview(CustomUser voter, SubjectReview subjectReview) {
        voteRepository.deleteByVoterAndSubjectReview(voter, subjectReview);
    }

    public boolean userAlreadyVotedOnReview(CustomUser voter, SubjectReview subjectReview) {
        return voteRepository.existsByVoterAndSubjectReview(voter, subjectReview);
    }

    public int getUserReviewVoteType(CustomUser voter, SubjectReview subjectReview) {
        Vote vote = voteRepository.findByVoterAndSubjectReview(voter, subjectReview);
        return vote.getVote();
    }

}
