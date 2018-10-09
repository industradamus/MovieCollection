package com.example.indus.moviecollection;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.indus.moviecollection.database.MovieDatabase;

public class App extends Application {
    public static App sInstance;
    private MovieDatabase mMovieDataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mMovieDataBase = Room.databaseBuilder(this, MovieDatabase.class, "moviedatabase").build();
    }

    public static App getsInstance() {
        return sInstance;
    }

    public  MovieDatabase getMovieDataBase() {
        return mMovieDataBase;
    }


}
