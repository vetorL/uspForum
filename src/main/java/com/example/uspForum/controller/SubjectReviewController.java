package com.example.uspForum.controller;

import com.example.uspForum.model.SubjectReview;
import com.example.uspForum.service.SubjectReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> delete(@PathVariable("id") long id) {

        SubjectReview subjectReview = subjectReviewService.findById(id);
        subjectReviewService.delete(subjectReview);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
