package com.example.uspForum.subjectReview;

import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.subject.Subject;
import org.springframework.data.repository.CrudRepository;

public interface SubjectReviewRepository extends CrudRepository<SubjectReview, Long> {

    boolean existsByAuthorAndSubject(CustomUser author, Subject subject);

}
