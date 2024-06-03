package com.example.uspForum.service;

import com.example.uspForum.repository.SubjectRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateSubjectService {

    private final SubjectRepository subjectRepository;

    public CreateSubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

}
