package com.example.indus.moviecollection.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.indus.moviecollection.R;
import com.example.indus.moviecollection.adapters.TheMovieRecyclerAdapter;
import com.example.indus.moviecollection.database.DatabaseCallback;
import com.example.indus.moviecollection.database.MovieDBManager;
import com.example.indus.moviecollection.model.TheMovieDetails;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView favoritesRecycler;
    private LinearLayoutManager layoutManager;
    private TheMovieRecyclerAdapter moviesAdapter;
    private List<TheMovieDetails> theMovies = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View favoritesFragment = inflater.inflate(R.layout.fragment_favorites, container, false);

        favoritesRecycler = favoritesFragment.findViewById(R.id.favorites_recycler);
        layoutManager = new LinearLayoutManager(favoritesFragment.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        favoritesRecycler.setLayoutManager(layoutManager);
        moviesAdapter = new TheMovieRecyclerAdapter(theMovies);
        favoritesRecycler.setAdapter(moviesAdapter);
        MovieDBManager.getInstance().getAllMovies(new DatabaseCallback<List<TheMovieDetails>>() {
            @Override
            public void onDataLoaded(List<TheMovieDetails> data) {
                if (data == null) {
                    Log.e("xxx", "no available data in db");
                    return;
                }
                theMovies = data;
                setupAdapter(theMovies);
            }

            @Override
            public void onActionSuccess() {
                Snackbar.make(favoritesRecycler, "Data Loaded", Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
        return favoritesFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        MovieDBManager.getInstance().getAllMovies(new DatabaseCallback<List<TheMovieDetails>>() {
            @Override
            public void onDataLoaded(List<TheMovieDetails> data) {
                if (data == null) {
                    Log.e("xxx", "no available data in db");
                    return;
                }
                theMovies = data;
                setupAdapter(theMovies);
            }

            @Override
            public void onActionSuccess() {
                Snackbar.make(favoritesRecycler, "Data Update", Snackbar.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void setupAdapter(List<TheMovieDetails> data) {
        favoritesRecycler.setLayoutManager(layoutManager);
        moviesAdapter = new TheMovieRecyclerAdapter(data);
        favoritesRecycler.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();
    }
}
