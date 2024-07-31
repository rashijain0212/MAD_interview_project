package com.example.numad22fa_team37.AtYourService.Models;

import com.google.gson.annotations.SerializedName;

public class Movie {
    @SerializedName("Title")
    public String title;
    @SerializedName("Year")
    public String year;
    public String imdbID;
    @SerializedName("Type")
    public String type;
    @SerializedName("Poster")
    public String poster;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return type;
    }

    public String getPoster() {
        return poster;
    }
}
