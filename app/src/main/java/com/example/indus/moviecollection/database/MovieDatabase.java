package com.example.indus.moviecollection.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.indus.moviecollection.model.TheMovieDetails;

@Database(entities = {TheMovieDetails.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract TheMovieDao theMovieDao();
}


