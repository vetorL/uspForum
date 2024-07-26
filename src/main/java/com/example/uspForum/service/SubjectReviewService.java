package com.example.uspForum.service;

import com.example.uspForum.exception.NotFoundException;
import com.example.uspForum.model.*;
import com.example.uspForum.repository.SubjectReviewRepository;
import com.example.uspForum.repository.VoteRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubjectReviewService {

    private final SubjectReviewRepository subjectReviewRepository;
    private final VoteRepository voteRepository;

    public SubjectReviewService(SubjectReviewRepository subjectReviewRepository, VoteRepository voteRepository) {
        this.subjectReviewRepository = subjectReviewRepository;
        this.voteRepository = voteRepository;
    }

    @PreAuthorize("#oldSubjectReview.author.username == authentication.principal.username")
    @Transactional
    public void update(SubjectReview oldSubjectReview, SubjectReviewDTO subjectReviewDTO) {
        SubjectReview updatedSubjectReview = new SubjectReview(oldSubjectReview.getAuthor(),
                oldSubjectReview.getSubject(), subjectReviewDTO.getTitle(), subjectReviewDTO.getContent(),
                subjectReviewDTO.getRecommendation());

        updatedSubjectReview.setId(oldSubjectReview.getId());

        subjectReviewRepository.save(updatedSubjectReview);
    }

    @PreAuthorize("#subjectReview.author.username == authentication.principal.username")
    public void delete(SubjectReview subjectReview) {
        subjectReviewRepository.delete(subjectReview);
    }

    public SubjectReview findById(long id) {
        return subjectReviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review não encontrada"));
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
