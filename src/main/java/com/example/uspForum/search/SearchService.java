package com.example.uspForum.search;

import com.example.uspForum.subject.Subject;
import com.example.uspForum.subject.SubjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private final int pageSize = 5;
    private final SubjectRepository subjectRepository;

    public SearchService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Page<Subject> searchSubjectByCode(String subjectCode, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        return subjectRepository.findSubjectByCode(subjectCode, page);
    }

    public Page<Subject> searchSubjectByName(String subjectName, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        return subjectRepository.findSubjectByName(subjectName, page);
    }

    public Page<Subject> searchSubjectByAbbreviation(String subjectAbbreviation, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        return subjectRepository.findSubjectByAbbreviation(subjectAbbreviation, page);
    }

    public Page<Subject> searchSubjectByTeacherName(String teacherName, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        return subjectRepository.findSubjectByTeacherName(teacherName, page);
    }

    public Page<Subject> searchSubjectBySearchText(String subjectText, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        return subjectRepository.findSubjectBySearchText(subjectText, page);
    }

}
