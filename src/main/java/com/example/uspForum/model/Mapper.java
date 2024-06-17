package com.example.uspForum.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Mapper {

    public Subject toSubject(SubjectCreationDTO subjectCreationDTO, Professor professor, Campus campus) {
        return new Subject(
                0,
                subjectCreationDTO.getName(),
                subjectCreationDTO.getAbbreviation(),
                subjectCreationDTO.getCode(),
                subjectCreationDTO.getRelatedCourse(),
                campus,
                professor,
                new ArrayList<>()
        );
    }

}
