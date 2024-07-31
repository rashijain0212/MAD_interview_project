package com.example.numad22fa_team37.AtYourService.Models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IMovieApi {

    @GET(".")
    Call<Search> getMoviesWithMultipleQueries(@Query("s") String title,
                                                   @Query("y") String year,
                                                   @Query("plot") String plot,
                                                   @Query("apikey") String apiKey);
}
