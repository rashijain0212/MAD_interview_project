package com.example.numad22fa_team37.AtYourService;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numad22fa_team37.AtYourService.Models.Movie;
import com.example.numad22fa_team37.R;

import java.util.ArrayList;

public class MovieView extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Movie> arrayList;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    MovieList movieList = (MovieList) this.getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);
        recyclerView = findViewById(R.id.recyclerMovieView);
        arrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = movieList.getArrayList();

        mAdapter = new RecyclerAdapter(arrayList, this);
        recyclerView.setAdapter(mAdapter);
    }

}