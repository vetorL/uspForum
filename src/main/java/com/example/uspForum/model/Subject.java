package com.example.uspForum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
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
