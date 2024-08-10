package com.example.uspForum.subjectReview.reviewReport;

import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.subjectReview.SubjectReview;
import org.springframework.data.repository.CrudRepository;

public interface ReviewReportRepository extends CrudRepository<ReviewReport, Long> {

    boolean existsBySubjectReviewAndAccuser(SubjectReview subjectReview, CustomUser accuser);

}
