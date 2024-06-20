package com.example.uspForum.service;

import com.example.uspForum.model.Subject;
import com.example.uspForum.model.SubjectReview;
import com.example.uspForum.repository.SubjectRepository;
import com.example.uspForum.repository.SubjectReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectReviewRepository subjectReviewRepository;

    public SubjectService(SubjectRepository subjectRepository,
                          SubjectReviewRepository subjectReviewRepository) {
        this.subjectRepository = subjectRepository;
        this.subjectReviewRepository = subjectReviewRepository;
    }

    public Optional<Subject> findSubjectById(Long id) {
        return subjectRepository.findById(id);
    }

    public void postSubjectReview(SubjectReview subjectReview) {
        subjectReviewRepository.save(subjectReview);
    }

}
