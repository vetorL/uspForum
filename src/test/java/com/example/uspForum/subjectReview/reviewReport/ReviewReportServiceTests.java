package com.example.uspForum.subjectReview.reviewReport;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewReportServiceTests {

    @Mock
    private ReviewReportRepository reviewReportRepository;

    @InjectMocks
    private ReviewReportService reviewReportService;

    @Test
    @DisplayName("registerReport works")
    void registerReportWorks() {
        // # Given:
        ReviewReport reviewReport = new ReviewReport();

        // # Call method to be tested
        reviewReportService.registerReport(reviewReport);

        // # Verify interactions with reviewReportRepository
        verify(reviewReportRepository, times(1)).save(reviewReport);
        verifyNoMoreInteractions(reviewReportRepository);
    }

}
