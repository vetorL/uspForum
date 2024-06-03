package com.example.uspForum.repository;

import com.example.uspForum.model.Subject;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Long> {

    @Query("SELECT * FROM subject WHERE abbreviation = :abbreviation")
    List<Subject> findSubjectByAbbreviation(String abbreviation);

    @Query("SELECT * FROM subject WHERE code = :code")
    List<Subject> findSubjectByCode(String code);

    @Query("SELECT * FROM subject WHERE name = :name")
    List<Subject> findSubjectByName(String name);

    @Query("SELECT * FROM subject WHERE teacher_name = :teacherName")
    List<Subject> findSubjectByTeacherName(String teacherName);

}
