package com.example.uspForum.controller;

import com.example.uspForum.model.CustomUser;
import com.example.uspForum.model.Subject;
import com.example.uspForum.model.SubjectReview;
import com.example.uspForum.model.SubjectReviewDTO;
import com.example.uspForum.service.SubjectReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews/{id}")
public class SubjectReviewController {

    private final SubjectReviewService subjectReviewService;

    public SubjectReviewController(SubjectReviewService subjectReviewService) {
        this.subjectReviewService = subjectReviewService;
    }

    @PutMapping
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody SubjectReviewDTO subjectReviewDTO,
                                    @AuthenticationPrincipal CustomUser author) {

        SubjectReview subjectReview = subjectReviewService.findById(id);
        Subject subject = subjectReview.getSubject();

        subjectReviewService.delete(subjectReview);
        subjectReviewService.create(subjectReviewDTO.toSubjectReview(author, subject));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@PathVariable("id") long id) {

        SubjectReview subjectReview = subjectReviewService.findById(id);
        subjectReviewService.delete(subjectReview);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
