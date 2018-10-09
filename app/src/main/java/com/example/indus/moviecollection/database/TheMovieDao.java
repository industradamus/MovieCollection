package com.example.indus.moviecollection.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.indus.moviecollection.model.TheMovie;
import com.example.indus.moviecollection.model.TheMovieDetails;

import java.util.List;

@Dao
public interface TheMovieDao {

    @Query("SELECT * FROM moviesdetails")
    List<TheMovieDetails> getAll();

    @Query("SELECT * FROM moviesdetails WHERE id = :id")
    TheMovieDetails getById(long id);

    @Insert
    void insert(TheMovieDetails movie);

    @Update
    void update(TheMovieDetails movie);

    @Delete
    void delete(TheMovieDetails movie);
}
