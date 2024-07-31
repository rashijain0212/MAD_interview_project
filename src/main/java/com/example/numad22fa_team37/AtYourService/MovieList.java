package com.example.numad22fa_team37.AtYourService;

import android.app.Application;

import com.example.numad22fa_team37.AtYourService.Models.Movie;

import java.util.ArrayList;

public class MovieList extends Application {

    private static final ArrayList<Movie> arrayList = new ArrayList<>();

    public static ArrayList<Movie> getArrayList() {
        return arrayList;
    }
}
