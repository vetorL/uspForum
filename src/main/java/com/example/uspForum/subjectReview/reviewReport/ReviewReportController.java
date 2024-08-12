package com.example.uspForum.subjectReview.reviewReport;

import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.subjectReview.SubjectReviewService;
import com.example.uspForum.util.ModelMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ReviewReportController {

    private final ReviewReportService reviewReportService;
    private final ModelMapper modelMapper;
    private final SubjectReviewService subjectReviewService;

    public ReviewReportController(ReviewReportService reviewReportService, ModelMapper modelMapper, SubjectReviewService subjectReviewService) {
        this.reviewReportService = reviewReportService;
        this.modelMapper = modelMapper;
        this.subjectReviewService = subjectReviewService;
    }

    @PostMapping("/reviews/{id}/report")
    public ResponseEntity<?> processReport(@PathVariable("id") Long id,
                                           @RequestBody @Valid ReviewReportDTO reviewReportDTO,
                                           @AuthenticationPrincipal CustomUser accuser) {

        ReviewReport reviewReport = modelMapper.toReviewReport(
                reviewReportDTO,
                accuser,
                subjectReviewService.findById(id));

        reviewReportService.registerReport(reviewReport);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
