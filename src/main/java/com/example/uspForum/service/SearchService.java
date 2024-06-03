package com.example.uspForum.service;

import com.example.uspForum.model.Subject;
import com.example.uspForum.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final SubjectRepository subjectRepository;

    public SearchService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public List<Subject> searchSubjectByCode(String subjectCode) {
        return subjectRepository.findSubjectByCode(subjectCode);
    }

    public List<Subject> searchSubjectByName(String subjectName) {
        return subjectRepository.findSubjectByName(subjectName);
    }

    public List<Subject> searchSubjectByAbbreviation(String subjectAbbreviation) {
        return subjectRepository.findSubjectByAbbreviation(subjectAbbreviation);
    }

    public List<Subject> searchSubjectByTeacherName(String teacherName) {
        return subjectRepository.findSubjectByTeacherName(teacherName);
    }

}
