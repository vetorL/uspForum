package com.example.uspForum.service;

import com.example.uspForum.model.Teacher;
import com.example.uspForum.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final TeacherRepository teacherRepository;

    public SearchService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<Teacher> searchTeacher(String teacherName) {
        return teacherRepository.findByTeacherName(teacherName);
    }

}
