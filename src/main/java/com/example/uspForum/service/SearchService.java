package com.example.uspForum.service;

import com.example.uspForum.repository.TeacherRepository;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private final TeacherRepository teacherRepository;

    public SearchService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

}
