package com.example.uspForum.controller;

import com.example.uspForum.model.CustomUser;
import com.example.uspForum.model.SubjectReview;
import com.example.uspForum.service.SubjectReviewService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reviews/{id}")
public class SubjectReviewController {

    private final SubjectReviewService subjectReviewService;

    public SubjectReviewController(SubjectReviewService subjectReviewService) {
        this.subjectReviewService = subjectReviewService;
    }

    @DeleteMapping
    public void delete(@PathVariable("id") long id,
                       @AuthenticationPrincipal CustomUser user) {

        SubjectReview subjectReview = subjectReviewService.findById(id);

        if (subjectReview != null) {
            subjectReviewService.delete(subjectReview);
        }
        
    }

}
