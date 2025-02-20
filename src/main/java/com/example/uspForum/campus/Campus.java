package com.example.uspForum.campus;

import com.example.uspForum.course.Course;
import com.example.uspForum.professor.Professor;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Campus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private final String name;

    private final String abbreviation;

    @OneToMany(mappedBy = "campus")
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "campus")
    private List<Professor> professors = new ArrayList<>();

}
