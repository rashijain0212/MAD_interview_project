package com.example.numad22fa_team37.FirebaseAssignment.Models;

import com.google.firebase.database.PropertyName;

public class Message {
    @PropertyName("to")
    private String to;
    @PropertyName("from")
    private String from;
    @PropertyName("sentOn")
    private Long sentOn;
    @PropertyName("stickerId")
    private Integer stickerId;

    public Message() {
    }

    public Message(String to, String from, Long sentOn, Integer stickerId) {
        this.to = to;
        this.from = from;
        this.sentOn = sentOn;
        this.stickerId = stickerId;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public Long getSentOn() {
        return sentOn;
    }

    public Integer getStickerId() {
        return stickerId;
    }
}
