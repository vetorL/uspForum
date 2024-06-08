package com.example.uspForum.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Subject {

    @Id
    private long id;

    private String name;
    private String abbreviation;
    private String code;
    private String relatedCourse;
    private String teacherName;

}
