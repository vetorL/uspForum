package com.example.uspForum.repository;

import com.example.uspForum.model.CustomUser;
import com.example.uspForum.model.Subject;
import com.example.uspForum.model.SubjectReview;
import org.springframework.data.repository.CrudRepository;

public interface SubjectReviewRepository extends CrudRepository<SubjectReview, Long> {

    boolean existsByAuthorAndSubject(CustomUser author, Subject subject);

}
