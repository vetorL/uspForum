package com.example.uspForum.model;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    private long id;

    private String username;
    private String password;

}
