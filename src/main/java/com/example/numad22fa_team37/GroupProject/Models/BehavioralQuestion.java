package com.example.numad22fa_team37.GroupProject.Models;

import com.google.firebase.database.PropertyName;

public class BehavioralQuestion {

    @PropertyName("id")
    private int id;
    @PropertyName("question")
    private String question;
    @PropertyName("sample")
    private String sample;

    public BehavioralQuestion() {
    }

    public BehavioralQuestion(int id, String question, String sample) {
        this.id = id;
        this.question = question;
        this.sample = sample;
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getSample() {
        return sample;
    }
}
