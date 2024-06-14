package com.example.uspForum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectCreationDTO {

    private String name;
    private String abbreviation;
    private String code;
    private String relatedCourse;
    private String relatedCampusId;
    private String teacherName;

}
