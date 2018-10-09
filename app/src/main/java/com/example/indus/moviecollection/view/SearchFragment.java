package com.example.indus.moviecollection.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.indus.moviecollection.R;
import com.example.indus.moviecollection.adapters.TheMovieRecyclerAdapter;
import com.example.indus.moviecollection.model.TheMovie;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private RecyclerView searchRecycler;
    private LinearLayoutManager layoutManager;
    private TheMovieRecyclerAdapter moviesAdapter;
    private List<TheMovie> theMovies = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View searchFragment = inflater.inflate(R.layout.fragment_search, container, false);
        searchRecycler = searchFragment.findViewById(R.id.search_recycler);
        layoutManager = new LinearLayoutManager(searchFragment.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        moviesAdapter = new TheMovieRecyclerAdapter(theMovies);
        searchRecycler.setLayoutManager(layoutManager);
        searchRecycler.setAdapter(moviesAdapter);
        return searchFragment;
    }


    private void setupAdapter(List<TheMovie> data){
        moviesAdapter = new TheMovieRecyclerAdapter(data);
        searchRecycler.setLayoutManager(layoutManager);
        searchRecycler.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();
    }

    public void setData(List<TheMovie> data){
        theMovies = data;
        setupAdapter(theMovies);
    }

    public void clear() {
        int size = this.theMovies.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.theMovies.remove(0);
            }
            moviesAdapter.notifyItemRangeRemoved(0, size);
        }
    }
}
