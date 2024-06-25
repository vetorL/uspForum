package com.example.uspForum.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private final String name;
    private final String normalizedName;
    private final String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(name = "campus_id")
    private final Campus campus;

    @OneToMany(mappedBy = "professor")
    private List<Subject> subjects = new ArrayList<>();

}
