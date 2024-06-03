package com.example.uspForum.model;

import org.springframework.data.annotation.Id;

public class Subject {

    @Id
    private long id;

    private String name;
    private String abbreviation;
    private String code;
    private String relatedCourse;
    private String teacherName;
    private long createdByAccountId;

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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRelatedCourse() {
        return relatedCourse;
    }

    public void setRelatedCourse(String relatedCourse) {
        this.relatedCourse = relatedCourse;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public long getCreatedByAccountId() {
        return createdByAccountId;
    }

    public void setCreatedByAccountId(long createdByAccountId) {
        this.createdByAccountId = createdByAccountId;
    }
}
