package com.example.uspForum.vote;

import com.example.uspForum.customUser.CustomUser;
import com.example.uspForum.subjectReview.SubjectReview;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private final int vote;

    @ManyToOne
    @JoinColumn(name = "voter_id")
    private final CustomUser voter;

    @ManyToOne
    @JoinColumn(name = "subject_review_id")
    private final SubjectReview subjectReview;

}
