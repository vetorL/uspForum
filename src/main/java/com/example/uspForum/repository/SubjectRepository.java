package com.example.uspForum.repository;

import com.example.uspForum.model.Subject;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Long> {

    List<Subject> findSubjectByAbbreviation(String abbreviation);

    List<Subject> findSubjectByCode(String code);

    List<Subject> findSubjectByName(String name);

    List<Subject> findSubjectByTeacherName(String teacherName);

}
