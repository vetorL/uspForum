package com.example.uspForum.subject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Long>,
        PagingAndSortingRepository<Subject, Long>, JpaRepository<Subject, Long> {

    @Query("SELECT s FROM Subject s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(s.abbreviation) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(s.code) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(s.normalizedName) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(s.course.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(s.professor.name) LIKE LOWER(CONCAT('%', :searchText, '%')) ")
    Page<Subject> findSubjectBySearchText(String searchText, Pageable pageable);

    @Query("SELECT s FROM Subject s WHERE " +
            "LOWER(s.abbreviation) LIKE LOWER(CONCAT('%', :abbreviation, '%'))")
    Page<Subject> findSubjectByAbbreviation(String abbreviation, Pageable pageable);

    @Query("SELECT s FROM Subject s WHERE " +
            "LOWER(s.code) LIKE LOWER(CONCAT('%', :code, '%'))")
    Page<Subject> findSubjectByCode(String code, Pageable pageable);

    @Query("SELECT s FROM Subject s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Subject> findSubjectByName(String name, Pageable pageable);

    @Query("SELECT s FROM Subject s WHERE " +
            "LOWER(s.professor.name) LIKE LOWER(CONCAT('%', :teacherName, '%'))")
    Page<Subject> findSubjectByTeacherName(String teacherName, Pageable pageable);

    List<Subject> findByCourseNormalizedNameAndCourseCampusAbbreviationAndAbbreviation(
            String course_normalizedName, String course_campus_abbreviation, String abbreviation);

    Subject findByCourseNormalizedNameAndCourseCampusAbbreviationAndAbbreviationAndProfessorNormalizedName(
            String course_normalizedName, String course_campus_abbreviation,
            String abbreviation, String professorNormalizedName);
}
