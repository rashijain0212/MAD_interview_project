package com.example.numad22fa_team37.GroupProject.Models;

import com.google.firebase.database.PropertyName;

public class TechnicalCourse {

    @PropertyName("id")
    private int id;
    @PropertyName("course")
    private String course;
    @PropertyName("content")
    private String content;

    public TechnicalCourse() {
    }

    public TechnicalCourse(int id, String course, String content) {
        this.id = id;
        this.course = course;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getCourse() {
        return course;
    }

    public String getContent() {
        return content;
    }
}
