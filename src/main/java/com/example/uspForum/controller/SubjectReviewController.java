package com.example.uspForum.controller;

import com.example.uspForum.model.*;
import com.example.uspForum.service.SubjectReviewService;
import com.example.uspForum.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SubjectReviewController {

    private final SubjectReviewService subjectReviewService;
    private final SubjectService subjectService;

    public SubjectReviewController(SubjectReviewService subjectReviewService, SubjectService subjectService) {
        this.subjectReviewService = subjectReviewService;
        this.subjectService = subjectService;
    }

    @PostMapping(value = "/subject/{subjectId}/reviews", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@PathVariable("subjectId") long subjectId,
                                    @AuthenticationPrincipal CustomUser author,
                                    @Valid @RequestBody SubjectReviewDTO subjectReviewDTO) {

        Subject associatedSubject = subjectService.findById(subjectId);
        SubjectReview subjectReview = subjectReviewDTO.toSubjectReview(author, associatedSubject);

        subjectService.postSubjectReview(subjectReview);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/reviews/{id}")
    @ResponseBody
    public ResponseEntity<?> update(@PathVariable("id") long id,
                                    @Valid @RequestBody SubjectReviewDTO subjectReviewDTO) {

        SubjectReview subjectReviewToBeUpdated = subjectReviewService.findById(id);
        subjectReviewService.update(subjectReviewToBeUpdated, subjectReviewDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {

        SubjectReview subjectReview = subjectReviewService.findById(id);
        subjectReviewService.delete(subjectReview);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
