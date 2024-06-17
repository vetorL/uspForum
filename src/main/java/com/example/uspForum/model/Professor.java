package com.example.uspForum.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public String name;
    public String email;

}
