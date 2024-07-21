package com.example.uspForum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectReviewResponse {

    private long id;
    private String title;
    private String content;
    private String recommendation;

    public static SubjectReviewResponse fromSubjectReview(SubjectReview subjectReview) {
        return new SubjectReviewResponse(
                subjectReview.getId(),
                subjectReview.getTitle(),
                subjectReview.getContent(),
                subjectReview.getRecommendation()
        );
    }

}
