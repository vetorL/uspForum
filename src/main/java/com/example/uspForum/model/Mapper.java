package com.example.uspForum.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Mapper {

    public Subject toSubject(SubjectCreationDTO subjectCreationDTO, Course course, Professor professor) {
        return new Subject(
                subjectCreationDTO.getName(),
                subjectCreationDTO.getAbbreviation(),
                subjectCreationDTO.getCode(),
                course,
                professor,
                new ArrayList<>()
        );
    }

}
