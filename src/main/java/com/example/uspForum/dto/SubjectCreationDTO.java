package com.example.uspForum.dto;

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
    private String relatedCourseName;
    private String relatedCampusAbbreviation;
    private String professorEmail;

}
