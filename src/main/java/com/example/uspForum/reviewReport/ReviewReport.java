package com.example.uspForum.reviewReport;

import com.example.uspForum.subjectReview.SubjectReview;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ReviewReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private final String reason;

    @ManyToOne
    @JoinColumn(name = "subject_review_id")
    private final SubjectReview subjectReview;
}
