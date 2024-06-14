package com.example.uspForum.model;

import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Subject toSubject(SubjectCreationDTO subjectCreationDTO, Campus campus) {
        return new Subject(
                0,
                subjectCreationDTO.getName(),
                subjectCreationDTO.getAbbreviation(),
                subjectCreationDTO.getCode(),
                subjectCreationDTO.getRelatedCourse(),
                campus,
                subjectCreationDTO.getTeacherName()
        );
    }

}
