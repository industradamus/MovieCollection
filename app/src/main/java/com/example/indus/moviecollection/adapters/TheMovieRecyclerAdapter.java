package com.example.indus.moviecollection.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.indus.moviecollection.App;
import com.example.indus.moviecollection.Const;
import com.example.indus.moviecollection.R;
import com.example.indus.moviecollection.model.TheMovie;
import com.example.indus.moviecollection.model.TheMovieDetails;
import com.example.indus.moviecollection.view.DetailsActivity;

import java.util.List;

public class TheMovieRecyclerAdapter extends RecyclerView.Adapter<TheMovieRecyclerAdapter.FavoritesMoviesHolder> {

    private List theMovies;

    public TheMovieRecyclerAdapter(List theMovies) {
        this.theMovies = theMovies;
    }

    @NonNull
    @Override
    public FavoritesMoviesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View favoritesItem = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.favorites_item, viewGroup, false);
        return new FavoritesMoviesHolder(favoritesItem);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesMoviesHolder favoritesMoviesHolder, int position) {
        final boolean isMovieDetails = theMovies.get(position) instanceof TheMovieDetails;
        String title = isMovieDetails ? ((TheMovieDetails) theMovies.get(position)).getTitle()
                : ((TheMovie) theMovies.get(position)).getTitle();
        String vote = String.valueOf(isMovieDetails ? ((TheMovieDetails) theMovies.get(position)).getVoteAverage()
                : ((TheMovie) theMovies.get(position)).getVoteAverage());
        String posterPath = isMovieDetails ? ((TheMovieDetails) theMovies.get(position)).getPosterPath()
                : ((TheMovie) theMovies.get(position)).getPosterPath();
        String releaseDate = isMovieDetails ? ((TheMovieDetails) theMovies.get(position)).getReleaseDate()
                : ((TheMovie) theMovies.get(position)).getReleaseDate();
        final int id = isMovieDetails ? ((TheMovieDetails) theMovies.get(position)).getId()
                : ((TheMovie) theMovies.get(position)).getId();

        Glide.with(App.getsInstance().getApplicationContext())
                .load(Const.BASE_IMAGE_URL +
                        posterPath)
                .into(favoritesMoviesHolder.posterPath);
        favoritesMoviesHolder.date.setText(title + " (" + releaseDate + ")");
        favoritesMoviesHolder.rating.setText(vote);

        favoritesMoviesHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openDetailsIntent = new Intent(view.getContext(), DetailsActivity.class);
                openDetailsIntent.setAction(isMovieDetails ? Const.ACTION_FROM_DB : Const.ACTION_FROM_NETWORK);
                openDetailsIntent.putExtra(Const.MOVIE_ID, id);
                view.getContext().startActivity(openDetailsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return theMovies.size();
    }

    static class FavoritesMoviesHolder extends RecyclerView.ViewHolder {
        private ImageView posterPath;
        private TextView date;
        private TextView rating;

        FavoritesMoviesHolder(@NonNull View itemView) {
            super(itemView);
            posterPath = itemView.findViewById(R.id.poster_path);
            date = itemView.findViewById(R.id.title_and_data);
            rating = itemView.findViewById(R.id.vote_average);
        }
    }
}
