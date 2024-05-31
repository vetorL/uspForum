package com.example.uspForum.model;

public class SubjectResult extends Subject {
    private String teacherName;

    public SubjectResult(Subject subject, String teacherName) {
        setId(subject.getId());
        setName(subject.getName());
        setTeacherName(teacherName);
        setCode(subject.getCode());
        setAbbreviation(subject.getAbbreviation());
        setRelatedCourse(subject.getRelatedCourse());
        setTeacherId(subject.getTeacherId());
        setCreatedByAccountId(subject.getCreatedByAccountId());
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherName() {
        return teacherName;
    }
}
