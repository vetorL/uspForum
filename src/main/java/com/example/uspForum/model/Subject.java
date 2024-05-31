package com.example.uspForum.model;

import org.springframework.data.annotation.Id;

public class Subject {

    @Id
    private long id;

    private String name;
    private String abbreviation;
    private String code;
    private String relatedCourse;
    private long teacherId;
    private long createdByAccountId;

}
