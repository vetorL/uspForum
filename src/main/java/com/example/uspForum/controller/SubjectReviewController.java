package com.example.uspForum.controller;

import com.example.uspForum.model.CustomUser;
import com.example.uspForum.model.SubjectReview;
import com.example.uspForum.model.SubjectReviewDTO;
import com.example.uspForum.model.SubjectReviewResponse;
import com.example.uspForum.service.SubjectReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SubjectReviewController {

    private final SubjectReviewService subjectReviewService;

    public SubjectReviewController(SubjectReviewService subjectReviewService) {
        this.subjectReviewService = subjectReviewService;
    }

    @PutMapping("/reviews/{id}")
    @ResponseBody
    public ResponseEntity<SubjectReviewResponse> update(@PathVariable("id") long id,
                                                        @Valid @RequestBody SubjectReviewDTO subjectReviewDTO,
                                                        @AuthenticationPrincipal CustomUser author) {

        SubjectReview oldSubjectReview = subjectReviewService.findById(id);
        SubjectReview newSubjectReview = subjectReviewDTO.toSubjectReview(author, oldSubjectReview.getSubject());

        SubjectReviewResponse subjectReviewResponse =
                subjectReviewService.deleteAndCreate(oldSubjectReview, newSubjectReview);

        return new ResponseEntity<SubjectReviewResponse>(subjectReviewResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {

        SubjectReview subjectReview = subjectReviewService.findById(id);
        subjectReviewService.delete(subjectReview);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
