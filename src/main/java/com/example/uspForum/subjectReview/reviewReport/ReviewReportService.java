package com.example.uspForum.subjectReview.reviewReport;

import com.example.uspForum.exception.NotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewReportService {

    private final ReviewReportRepository reviewReportRepository;

    public ReviewReportService(ReviewReportRepository reviewReportRepository) {
        this.reviewReportRepository = reviewReportRepository;
    }

    @PreAuthorize("authenticated")
    public void registerReport(ReviewReport reviewReport) {

        // If the user has already reported the review, do not save
        if(reviewReportRepository.existsBySubjectReviewAndAccuser(reviewReport.getSubjectReview(),
                                                                  reviewReport.getAccuser())) {
            return;
        }

        // If it is the first time the user has reported the review, save
        reviewReportRepository.save(reviewReport);
    }

    public ReviewReport findById(long id) {
        return reviewReportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Denúncia não encontrada"));
    }

    public List<ReviewReport> getActiveReviewReports() {
        return reviewReportRepository.findByArchivedFalse();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void archiveReport(ReviewReport reviewReport) {
        reviewReport.setArchived(true);
        reviewReportRepository.save(reviewReport);
    }

}
