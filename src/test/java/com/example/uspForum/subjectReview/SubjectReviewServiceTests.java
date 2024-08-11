package com.example.uspForum.subjectReview;

import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.subject.Subject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubjectReviewServiceTests {

    @Mock
    private SubjectReviewRepository subjectReviewRepository;

    @InjectMocks
    private SubjectReviewService subjectReviewService;

    @Test
    @DisplayName("update works")
    void updateWorks() {
        // # Given:
        SubjectReviewDTO subjectReviewDTO = new SubjectReviewDTO("title", "content", "Neutro");

        SubjectReview oldSubjectReview = new SubjectReview(new CustomUser(), new Subject(), "title",
                "content", "recommendation");

        // # Call method to be tested
        subjectReviewService.update(oldSubjectReview, subjectReviewDTO);

        // # Updated subject review that should be saved
        SubjectReview updatedSubjectReview = new SubjectReview(oldSubjectReview.getAuthor(),
                oldSubjectReview.getSubject(), subjectReviewDTO.getTitle(), subjectReviewDTO.getContent(),
                subjectReviewDTO.getRecommendation());
        // hasBeenEdited should now be true
        updatedSubjectReview.setHasBeenEdited(true);

        // # Verify interactions with subjectReviewRepository
        verify(subjectReviewRepository, times(1)).save(updatedSubjectReview);
        verifyNoMoreInteractions(subjectReviewRepository);
    }

    @Test
    @DisplayName("delete works")
    void deleteWorks() {
        // # Given
        SubjectReview subjectReview = new SubjectReview(new CustomUser(), new Subject(), "title", "content",
                "Neutro");

        // # Call method to be tested
        subjectReviewService.delete(subjectReview);

        // # Verify interactions with subjectReviewRepository
        verify(subjectReviewRepository, times(1)).delete(subjectReview);
        verifyNoMoreInteractions(subjectReviewRepository);
    }

}
