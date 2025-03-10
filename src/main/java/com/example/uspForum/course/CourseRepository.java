package com.example.uspForum.course;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course, Long> {

    List<Course> findByNameAndCampusAbbreviation(String name, String campusAbbreviation);

    Course findByNormalizedNameAndCampusAbbreviation(String normalizedName, String campus_abbreviation);

    @Query("SELECT DISTINCT name FROM Course")
    List<String> findAllDistinctCourseNames();

}
