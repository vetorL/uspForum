package com.example.uspForum.subjectReview.reviewReport;

import org.springframework.stereotype.Service;

@Service
public class ReviewReportService {

    private final ReviewReportRepository reviewReportRepository;

    public ReviewReportService(ReviewReportRepository reviewReportRepository) {
        this.reviewReportRepository = reviewReportRepository;
    }

    public void registerReport(ReviewReport reviewReport) {
        reviewReportRepository.save(reviewReport);
    }

}
