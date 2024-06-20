package com.example.uspForum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectReviewDTO {

    private String title;
    private String content;

    public SubjectReview toSubjectReview(CustomUser author, Subject subject) {
        return new SubjectReview(
                author,
                subject,
                title,
                content
        );
    }

}
