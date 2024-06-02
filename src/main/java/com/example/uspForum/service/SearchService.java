package com.example.uspForum.service;

import com.example.uspForum.model.SubjectResult;
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

    public List<SubjectResult> searchSubjectByCode(String subjectCode) {
        List<SubjectResult> subjectResults = subjectRepository.findSubjectByCode(subjectCode)
                .stream()
                .map(subject -> new SubjectResult(
                        subject,
                        teacherRepository
                                .findByTeacherId(subject.getTeacherId())
                                .getName()
                )).toList();
        return subjectResults;
    }

    public List<SubjectResult> searchSubjectByName(String subjectName) {
        List<SubjectResult> subjectResults = subjectRepository.findSubjectByName(subjectName)
                .stream()
                .map(subject -> new SubjectResult(
                        subject,
                        teacherRepository
                                .findByTeacherId(subject.getTeacherId())
                                .getName()
                )).toList();
        return subjectResults;
    }

    public List<SubjectResult> searchSubjectByAbbreviation(String subjectAbbreviation) {
        List<SubjectResult> subjectResults = subjectRepository.findSubjectByAbbreviation(subjectAbbreviation)
                .stream()
                .map(subject -> new SubjectResult(
                        subject,
                        teacherRepository
                                .findByTeacherId(subject.getTeacherId())
                                .getName()
                )).toList();
        return subjectResults;
    }

}
