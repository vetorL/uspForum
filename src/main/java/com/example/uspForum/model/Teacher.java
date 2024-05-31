package com.example.uspForum.model;

import org.springframework.data.annotation.Id;

public class Teacher {

    @Id
    private long id;

    private String name;
    private String email;
    private String teachesAt;

}
