package com.example.numad22fa_team37.AtYourService.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Search {
    @SerializedName("Search")
    private List<Movie> search;


    public List<Movie> getSearch() {
        return search;
    }
}
