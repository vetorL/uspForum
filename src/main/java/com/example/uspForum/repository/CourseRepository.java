package com.example.uspForum.repository;

import com.example.uspForum.model.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {

    List<Course> findByNameAndCampusAbbreviation(String name, String campusAbbreviation);

}
