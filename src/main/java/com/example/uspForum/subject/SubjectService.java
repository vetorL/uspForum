package com.example.uspForum.subject;

import com.example.uspForum.exception.NotFoundException;
import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.subjectReview.SubjectReview;
import com.example.uspForum.subjectReview.SubjectReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectReviewRepository subjectReviewRepository;

    public SubjectService(SubjectRepository subjectRepository,
                          SubjectReviewRepository subjectReviewRepository) {
        this.subjectRepository = subjectRepository;
        this.subjectReviewRepository = subjectReviewRepository;
    }

    public Subject create(Subject subject) {
        return subjectRepository.save(subject);
    }

    public Subject findById(Long id) {
        return subjectRepository.findById(id).orElseThrow(() -> new NotFoundException("Disciplina não encontrada"));
    }

    public void postSubjectReview(SubjectReview subjectReview) {
        subjectReviewRepository.save(subjectReview);
    }

    public boolean userAlreadyPostedReview(CustomUser author, Subject subject) {
        return subjectReviewRepository.existsByAuthorAndSubject(author, subject);
    }

    public List<Subject> findByCourseNormalizedNameAndCourseCampusAbbreviationAndAbbreviation(
            String courseNormalizedName, String campusAbbreviation, String subjectAbbreviation) {
        return subjectRepository.findByCourseNormalizedNameAndCourseCampusAbbreviationAndAbbreviation(
                courseNormalizedName, campusAbbreviation, subjectAbbreviation);
    };

    public Subject findByCourseAndCampusAndSubjectAndProfessor(
            String courseNormalizedName, String campusAbbreviation,
            String subjectAbbreviation, String professorNormalizedName
    ) {
        return subjectRepository.findByCourseNormalizedNameAndCourseCampusAbbreviationAndAbbreviationAndProfessorNormalizedName(
                courseNormalizedName, campusAbbreviation, subjectAbbreviation, professorNormalizedName);
    }

}
