package com.example.uspForum.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor(force = true)
@Table(uniqueConstraints = {@UniqueConstraint(name = "UniqueNameAndProfessorAndCourse",
        columnNames = {"name", "professor_id", "course_id"})})
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private final String name;
    private String normalizedName;
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

    public Subject(String name, String abbreviation, String code, Course course, Professor professor) {
        this.name = name;
        this.normalizedName = normalizeName(name);
        this.abbreviation = abbreviation;
        this.code = code;
        this.course = course;
        this.professor = professor;
    }

    private String normalizeName(String name) {
        name = Normalizer.normalize(name, Normalizer.Form.NFD);
        name = name.replaceAll("[^\\p{ASCII}]", "");
        return name.toLowerCase();
    }

}
