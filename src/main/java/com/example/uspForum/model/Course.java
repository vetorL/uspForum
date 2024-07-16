package com.example.uspForum.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(name = "UniqueNameAndCampus",
        columnNames = {"name", "campus_id"})})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private final String name;
    private final String normalizedName;

    private String gradePictureUrl;

    @ManyToOne
    @JoinColumn(name = "campus_id")
    private final Campus campus;

    @OneToMany(mappedBy = "course")
    private List<Subject> subjects = new ArrayList<>();

}
