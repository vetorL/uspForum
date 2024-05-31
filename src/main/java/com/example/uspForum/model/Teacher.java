package com.example.uspForum.model;

import org.springframework.data.annotation.Id;

public class Teacher {

    @Id
    private long id;

    private String name;
    private String email;
    private String teachesAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeachesAt() {
        return teachesAt;
    }

    public void setTeachesAt(String teachesAt) {
        this.teachesAt = teachesAt;
    }
}
