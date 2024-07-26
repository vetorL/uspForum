package com.example.uspForum.util;

import com.example.uspForum.course.Course;
import com.example.uspForum.subject.SubjectCreationDTO;
import com.example.uspForum.professor.Professor;
import com.example.uspForum.subject.Subject;
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
