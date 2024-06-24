package com.example.uspForum.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private final String name;
    private final String abbreviation;
    private final String code;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(name = "course_id")
    private final Course course;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(name = "professor_id")
    private final Professor professor;

    @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<SubjectReview> reviews = new ArrayList<>();

}
