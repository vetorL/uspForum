package com.example.uspForum.service;

import com.example.uspForum.model.Subject;
import com.example.uspForum.model.Teacher;
import com.example.uspForum.repository.SubjectRepository;
import com.example.uspForum.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public SearchService(TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Teacher> searchTeacher(String teacherName) {
        return teacherRepository.findByTeacherName(teacherName);
    }

    public List<Subject> searchSubjectByAbbreviation(String subjectAbbreviation) {
        return subjectRepository.findSubjectByAbbreviation(subjectAbbreviation);
    }

}
