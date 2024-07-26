package com.example.uspForum.subject;

import org.springframework.stereotype.Service;

@Service
public class CreateSubjectService {

    private final SubjectRepository subjectRepository;

    public CreateSubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject createSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

}
