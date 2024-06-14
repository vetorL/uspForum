package com.example.uspForum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String abbreviation;
    private String code;
    private String relatedCourse;

    @ManyToOne
    private Campus relatedCampus;

    private String teacherName;

}
