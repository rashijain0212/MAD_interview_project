package com.example.numad22fa_team37.AtYourService;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.numad22fa_team37.AtYourService.Models.IMovieApi;
import com.example.numad22fa_team37.AtYourService.Models.Movie;
import com.example.numad22fa_team37.AtYourService.Models.Search;
import com.example.numad22fa_team37.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AtYourServiceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private final String Tag = "AtYourServiceActivity";
    private String movieName;
    private String movieYear;
    private String plotItem;
    private Spinner plot;
    private EditText title;
    private EditText year;
    private ArrayList<Movie> arrayList;

    private ProgressBar pgsBar;
    private IMovieApi api;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);
        arrayList = MovieList.getArrayList();
        Log.v(Tag, "onCreate Called");
        plot = findViewById(R.id.plot);
        Button search = findViewById(R.id.search);

        rootView = findViewById(R.id.constraintLayout);
        pgsBar = findViewById(R.id.pBar);

        ArrayAdapter<CharSequence> plotAdapter = ArrayAdapter.createFromResource(this,
                R.array.plot, android.R.layout.simple_spinner_item);
        plotAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plot.setAdapter(plotAdapter);
        plot.setOnItemSelectedListener(AtYourServiceActivity.this);


        ArrayAdapter<CharSequence> responseAdapter = ArrayAdapter.createFromResource(this,
                R.array.response, android.R.layout.simple_spinner_item);
        responseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        api = retrofit.create(IMovieApi.class);
//        reset.setOnClickListener(view -> {
//            title = findViewById(R.id.name);
//            year = findViewById(R.id.year);
//            title.setText(" ");
//            year.setText(" ");
//            plot.setSelection(0);
//        });

        search.setOnClickListener(view -> {
            title = findViewById(R.id.name);

            year = findViewById(R.id.year);

            movieName = title.getText().toString();
            movieYear = year.getText().toString();
            plot.setSelection(0);
            pgsBar.setVisibility(View.VISIBLE);

            if ((movieName.length()) > 3) {
                getMovies();
            } else {
                showSnackBar(rootView, "Name of the move should be more than three characters");
                pgsBar.setVisibility(View.GONE);
            }
        });
    }

    void openMovieActivity() {
        Intent intent = new Intent(this, MovieView.class);
        startActivity(intent);
    }

    private void getMovies() {

        Call<Search> call = api.getMoviesWithMultipleQueries(movieName, movieYear, plotItem, "1f42b3cb");

        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                if (!response.isSuccessful()) {
                    Log.d(Tag, "Call failed!" + response.code());
                    return;
                }

                Log.d(Tag, "Call Successes!");
                Search movieModels = response.body();

                if (movieModels.getSearch() != null) {
                    StringBuilder str = new StringBuilder();
                    for (Movie movie : movieModels.getSearch()) {
                        //feed data to the recycler view.
                        str.append("Title : ")
                                .append(movie.getTitle())
                                .append("\n");
                        arrayList.add(movie);
                    }

                    Log.d(Tag, str.toString());

                    try {
                        Thread.sleep(2000);
                        pgsBar.setVisibility(View.GONE);

                    } catch (Exception e) {
                        //handle exception
                    }

                    openMovieActivity();
                } else {
                    showSnackBar(rootView, "Invalid Movie Name or Invalid Year");
                    pgsBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                Log.d(Tag, "Call failed!" + t.getMessage());
                pgsBar.setVisibility(View.GONE);
                showSnackBar(rootView, "Something went wrong! Please try again!");
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        plotItem = (String) plot.getItemAtPosition(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        plotItem = (String) plot.getItemAtPosition(0);
    }


    private void showSnackBar(View rootView, String message) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show();
    }
}
