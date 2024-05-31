package com.example.uspForum.repository;

import com.example.uspForum.model.Subject;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Long> {

    @Query("SELECT * FROM subject WHERE abbreviation = :abbreviation")
    List<Subject> findSubjectByAbbreviation(String abbreviation);

}
