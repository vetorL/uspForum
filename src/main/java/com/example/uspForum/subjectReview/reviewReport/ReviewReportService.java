package com.example.uspForum.subjectReview.reviewReport;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewReportService {

    private final ReviewReportRepository reviewReportRepository;

    public ReviewReportService(ReviewReportRepository reviewReportRepository) {
        this.reviewReportRepository = reviewReportRepository;
    }

    public void registerReport(ReviewReport reviewReport) {
        reviewReportRepository.save(reviewReport);
    }

    public List<ReviewReport> findAll() {
        List<ReviewReport> reviewReports = new ArrayList<>();
        reviewReportRepository.findAll().forEach(reviewReports::add);
        return reviewReports;
    }

}
