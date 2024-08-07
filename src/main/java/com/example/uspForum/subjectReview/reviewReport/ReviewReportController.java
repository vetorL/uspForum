package com.example.uspForum.subjectReview.reviewReport;

import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.subjectReview.SubjectReviewService;
import com.example.uspForum.util.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews/{id}/report")
public class ReviewReportController {

    private final ReviewReportService reviewReportService;
    private final ModelMapper modelMapper;
    private final SubjectReviewService subjectReviewService;

    public ReviewReportController(ReviewReportService reviewReportService, ModelMapper modelMapper, SubjectReviewService subjectReviewService) {
        this.reviewReportService = reviewReportService;
        this.modelMapper = modelMapper;
        this.subjectReviewService = subjectReviewService;
    }

    @PostMapping
    public void processReport(@PathVariable("id") Long id,
                              @RequestBody ReviewReportDTO reviewReportDTO,
                              @AuthenticationPrincipal CustomUser accuser) {

        ReviewReport reviewReport = modelMapper.toReviewReport(
                reviewReportDTO,
                accuser,
                subjectReviewService.findById(id));

        reviewReportService.registerReport(reviewReport);
    }

}
