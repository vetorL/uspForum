package com.example.uspForum.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public final String name;
    public final String email;

}
