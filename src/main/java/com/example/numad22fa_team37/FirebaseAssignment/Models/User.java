package com.example.numad22fa_team37.FirebaseAssignment.Models;

import com.google.firebase.database.PropertyName;

public class User {
    @PropertyName("username")
    private String username;

    @PropertyName("token")
    private String token;

    public User() {
    }

    public User(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
