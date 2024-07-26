package com.example.uspForum.subject;

import com.example.uspForum.course.Course;
import com.example.uspForum.professor.Professor;
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

    public Subject toSubject(Course course, Professor professor) {
        return new Subject(
                this.getName(),
                this.getAbbreviation(),
                this.getCode(),
                course,
                professor
        );
    }

}
