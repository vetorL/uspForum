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

    @Query("SELECT s FROM Subject s WHERE " +
            "LOWER(s.abbreviation) LIKE LOWER(CONCAT('%', :abbreviation, '%'))")
    List<Subject> findSubjectByAbbreviation(String abbreviation);

    @Query("SELECT s FROM Subject s WHERE " +
            "LOWER(s.code) LIKE LOWER(CONCAT('%', :code, '%'))")
    List<Subject> findSubjectByCode(String code);

    @Query("SELECT s FROM Subject s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Subject> findSubjectByName(String name);

    @Query("SELECT s FROM Subject s WHERE " +
            "LOWER(s.teacherName) LIKE LOWER(CONCAT('%', :teacherName, '%'))")
    List<Subject> findSubjectByTeacherName(String teacherName);

}
