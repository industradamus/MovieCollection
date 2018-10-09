package com.example.indus.moviecollection.network;

import com.example.indus.moviecollection.model.MyResponse;
import com.example.indus.moviecollection.model.TheMovieDetails;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface INetwork {

    @GET("search/movie?")
    Call<MyResponse> search(@QueryMap Map<String, String> params);

    @GET("movie/{id}?")
    Call<TheMovieDetails> searchById(@Path("id") int movieId, @QueryMap Map<String, String> params);
}
