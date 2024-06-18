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
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private final String name;
    private final String abbreviation;
    private final String code;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private final Course course;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private final Professor professor;

    @OneToMany(mappedBy = "subject")
    private List<SubjectReview> reviews = new ArrayList<>();

}
