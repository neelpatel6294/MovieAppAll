package com.example.movieappre.service;

import com.example.movieappre.model.Movies.Movies;
import com.example.movieappre.model.Reviews.Reviews;
import com.example.movieappre.model.Trailer.Trailers;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    // query for movies

    @GET("movie/{filter}")
    Observable<Movies> getMovies(@Path("filter") String filter, @Query("api_key") String apiKey);


    // query for movie trailers

    @GET("movie/{id}/reviews")
    Observable<Reviews> getMovieReviews(@Path("id") int id, @Query("api_key") String apiKey);

    // query for movie trailers

    @GET("movie/{id}/videos")
    Observable<Trailers> getMovieTrailers(@Path("id") int id, @Query("api_key") String apiKey);


}
