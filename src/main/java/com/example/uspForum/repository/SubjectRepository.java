package com.example.uspForum.repository;

import com.example.uspForum.model.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Long> {

    @Query("SELECT s FROM Subject s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(s.abbreviation) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(s.code) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(s.relatedCourse) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(s.teacherName) LIKE LOWER(CONCAT('%', :searchText, '%')) ")
    List<Subject> findSubjectBySearchText(String searchText);

    List<Subject> findSubjectByAbbreviation(String abbreviation);

    List<Subject> findSubjectByCode(String code);

    List<Subject> findSubjectByName(String name);

    List<Subject> findSubjectByTeacherName(String teacherName);

}
