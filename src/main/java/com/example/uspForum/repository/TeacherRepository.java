package com.example.uspForum.repository;

import com.example.uspForum.model.Teacher;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {

    @Query("SELECT * FROM teacher WHERE id = :id")
    Teacher findByTeacherId(long id);

}
