package com.example.uspForum.course;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<String> getAllDistinctCourseNames() {
        return courseRepository.findAllDistinctCourseNames();
    }

    public List<Course> findByNameAndCampusAbbreviation(String name, String campusAbbreviation) {
        return courseRepository.findByNameAndCampusAbbreviation(name, campusAbbreviation);
    }

    public Course findByNormalizedNameAndCampusAbbreviation(String normalizedName, String campusAbbreviation) {
        return courseRepository.findByNormalizedNameAndCampusAbbreviation(normalizedName, campusAbbreviation);
    }

}
