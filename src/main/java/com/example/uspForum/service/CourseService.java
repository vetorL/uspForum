package com.example.uspForum.service;

import com.example.uspForum.model.Course;
import com.example.uspForum.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        courseRepository.findAll().forEach(courses::add);
        return courses;
    }

    public List<Course> findByNameAndCampusAbbreviation(String name, String campusAbbreviation) {
        return courseRepository.findByNameAndCampusAbbreviation(name, campusAbbreviation);
    }

}
