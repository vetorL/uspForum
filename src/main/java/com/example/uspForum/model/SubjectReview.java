package com.example.uspForum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class SubjectReview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private final CustomUser author;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private final Subject subject;

    @OneToMany(mappedBy = "subjectReview")
    private List<Vote> votes = new ArrayList<>();

    private final String title;
    private final String content;

    public long getTotalVotes() {
        return votes.stream()
                .mapToLong(Vote::getVote)
                .sum();
    }

}
