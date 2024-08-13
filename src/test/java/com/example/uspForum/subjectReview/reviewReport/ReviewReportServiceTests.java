package com.example.uspForum.subjectReview.reviewReport;

import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.exception.NotFoundException;
import com.example.uspForum.subjectReview.SubjectReview;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
        ReviewReport reviewReport = new ReviewReport("reason", new CustomUser(), new SubjectReview());

        // # Call method to be tested
        reviewReportService.registerReport(reviewReport);

        // # Verify interactions with reviewReportRepository
        verify(reviewReportRepository, times(1))
                .existsBySubjectReviewAndAccuser(any(SubjectReview.class), any(CustomUser.class));
        verify(reviewReportRepository, times(1))
                .save(reviewReport);
        verifyNoMoreInteractions(reviewReportRepository);
    }

    @Test
    @DisplayName("registerReport works when a report already exists")
    void registerReportWorksWhenReportAlreadyExists() {
        // # Given:
        ReviewReport reviewReport = new ReviewReport("reason", new CustomUser(), new SubjectReview());

        // # Mock return value of called method
        when(reviewReportRepository.existsBySubjectReviewAndAccuser(any(SubjectReview.class), any(CustomUser.class)))
                .thenReturn(true);

        // # Call method to be tested
        reviewReportService.registerReport(reviewReport);

        // # Verify interactions with reviewReportRepository
        verify(reviewReportRepository, times(1))
                .existsBySubjectReviewAndAccuser(any(SubjectReview.class), any(CustomUser.class));
        verify(reviewReportRepository, never())
                .save(reviewReport);
        verifyNoMoreInteractions(reviewReportRepository);
    }

    @Test
    @DisplayName("getActiveReviewReports works")
    void getActiveReviewReportsWorks() {
        // # Call method to be tested
        reviewReportService.getActiveReviewReports();

        // # Verify interactions with reviewReportRepository
        verify(reviewReportRepository, times(1)).findByArchivedFalse();
        verifyNoMoreInteractions(reviewReportRepository);
    }

    @Test
    @DisplayName("archiveReport works")
    void archiveReportWorks() {
        // # Given:
        ReviewReport reviewReport = new ReviewReport("Outro", new CustomUser(), new SubjectReview());

        // archived property should be false before archiveReport is called
        assertFalse(reviewReport.isArchived());

        // # Call method to be tested
        reviewReportService.archiveReport(reviewReport);

        // archived property should now be true
        assertTrue(reviewReport.isArchived());

        // # Verify interactions with reviewReportRepository
        verify(reviewReportRepository, times(1)).save(reviewReport);
        verifyNoMoreInteractions(reviewReportRepository);
    }

    @Test
    @DisplayName("findById works when report exists")
    void findByIdWorksWhenReportExists() {
        // # Given
        long reportId = 1L;
        ReviewReport reviewReport = new ReviewReport("Outro", new CustomUser(), new SubjectReview());

        // # Mock return value of called method
        when(reviewReportRepository.findById(reportId)).thenReturn(Optional.of(reviewReport));

        // # Call method to be tested
        reviewReportService.findById(reportId);

        // # Verify interactions with reviewReportRepository
        verify(reviewReportRepository, times(1)).findById(reportId);
        verifyNoMoreInteractions(reviewReportRepository);
    }

    @Test
    @DisplayName("findById works when report does not exist")
    void findByIdWorksWhenReportDoesNotExist() {
        // # Given
        long reportId = 1L;
        ReviewReport reviewReport = new ReviewReport("Outro", new CustomUser(), new SubjectReview());

        // # Mock return value of called method
        when(reviewReportRepository.findById(reportId)).thenReturn(Optional.empty());

        // # Call method to be tested
        assertThrows(NotFoundException.class, () -> reviewReportService.findById(reportId));

        // # Verify interactions with reviewReportRepository
        verify(reviewReportRepository, times(1)).findById(reportId);
        verifyNoMoreInteractions(reviewReportRepository);
    }

}
