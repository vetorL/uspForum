package com.example.uspForum.model;

import com.example.uspForum.dto.SubjectCreationDTO;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public Subject toSubject(SubjectCreationDTO subjectCreationDTO, Course course, Professor professor) {
        return new Subject(
                subjectCreationDTO.getName(),
                subjectCreationDTO.getAbbreviation(),
                subjectCreationDTO.getCode(),
                course,
                professor
        );
    }

}
