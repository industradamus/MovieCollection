package com.example.indus.moviecollection.network;

import android.util.Log;
import com.example.indus.moviecollection.Const;
import com.example.indus.moviecollection.database.MovieDBManager;
import com.example.indus.moviecollection.model.MyResponse;
import com.example.indus.moviecollection.model.TheMovieDetails;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static Network sInstance;
    private static Retrofit sRetrofit;
    private static INetwork sService;

    private MovieDBManager movieDBManager;

    private Network() {

        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        sRetrofit = new Retrofit.Builder()
                .baseUrl(Const.BASE_URL)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sService = sRetrofit.create(INetwork.class);

    }

    public static Network getInstance() {
        if (sInstance == null) {
            sInstance = new Network();
        }
        return sInstance;
    }

    public void searchMovie(String searchText, final IOnDataLoaded callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("query",searchText);
        params.put("api_key",Const.API_KEY);
        params.put("language","ru-RU");
        sService.search(params).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, final Response<MyResponse> response) {
                log("onResponse: " +response.isSuccessful());
                log("request: " + call.request().url().toString());
                log("response" + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    MyResponse myResponse = response.body();
                    callback.onNewDataLoaded(myResponse.getResults());
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                t.printStackTrace();
                callback.onErrorDataLoaded();
            }
        });
    }

    public void searchMovieById(final int movieId, final IOnDataLoaded callback) {

        HashMap<String, String> params = new HashMap<>();
        params.put("query", String.valueOf(movieId));
        params.put("api_key",Const.API_KEY);
        params.put("language","ru-RU");
        sService.searchById(movieId, params).enqueue(new Callback<TheMovieDetails>() {
            @Override
            public void onResponse(Call<TheMovieDetails> call, Response<TheMovieDetails> response) {
                if (response != null && response.isSuccessful() && response.body() != null) {
                    log("request" + call.request().url().toString());
                    log("response" + response.body().toString());
                    TheMovieDetails theMovieDetails = response.body();
                    callback.onNewDataLoaded(theMovieDetails);
                }
            }

            @Override
            public void onFailure(Call<TheMovieDetails> call, Throwable t) {
                callback.onErrorDataLoaded();
            }
        });
    }

    private void log(String text) {
        Log.e("MyLogs", text);
    }

}
