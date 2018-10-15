package com.example.indus.moviecollection.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.indus.moviecollection.App;
import com.example.indus.moviecollection.Const;
import com.example.indus.moviecollection.R;
import com.example.indus.moviecollection.database.DatabaseCallback;
import com.example.indus.moviecollection.database.MovieDBManager;
import com.example.indus.moviecollection.model.TheMovieDetails;
import com.example.indus.moviecollection.network.IOnDataLoaded;
import com.example.indus.moviecollection.network.Network;

public class DetailsActivity extends AppCompatActivity {

    private ImageView posterPath;
    private ImageView backdropPath;
    private TextView title;
    private TextView released;
    private TextView language;
    private TextView overview;
    private TextView votes;
    private TextView rating;
    private TextView popularity;
    private RatingBar ratingBar;
    private ImageView favoritesButton;
    private boolean isDataFromDB;
    private TheMovieDetails theMovieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initViews();
        Intent intent = getIntent();
        String action = intent.getAction();
        int idMovie = intent.getIntExtra(Const.MOVIE_ID, -1);
        if(TextUtils.isEmpty(action) || idMovie  == -1){
            //отборазить ошибку
            return;
        }

        Log.e("xxx","details activity action is: " + action);
        switch (action){
            case Const.ACTION_FROM_DB:
                MovieDBManager.getInstance().getMovieById(idMovie, new DatabaseCallback<TheMovieDetails>() {
                    @Override
                    public void onDataLoaded(TheMovieDetails data) {
                        theMovieDetails = data;
                        isDataFromDB = true;
                        showMovie();
                        setRelevantIconToButton();
                    }

                    @Override
                    public void onActionSuccess() {

                    }
                });
                break;
            case Const.ACTION_FROM_NETWORK:
                Network.getInstance().searchMovieById(idMovie, new IOnDataLoaded<TheMovieDetails>() {
                    @Override
                    public void onNewDataLoaded(TheMovieDetails data) {
                        theMovieDetails = data;
                        isDataFromDB = false;
                        showMovie();
                        setRelevantIconToButton();
                    }

                    @Override
                    public void onErrorDataLoaded() {

                    }
                });
                break;
        }

        setRelevantIconToButton();
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (theMovieDetails == null){
                    return;
                }

                if(isDataFromDB){
                        MovieDBManager.getInstance().deleteMovie(theMovieDetails, new DatabaseCallback() {
                            @Override
                            public void onDataLoaded(Object data) {

                            }

                            @Override
                            public void onActionSuccess() {
                                isDataFromDB = false;
                                setRelevantIconToButton();
                            }

                        });

                    return;
                }

                MovieDBManager.getInstance().insertMovie(theMovieDetails, new DatabaseCallback() {
                    @Override
                    public void onDataLoaded(Object data) {

                    }

                    @Override
                    public void onActionSuccess() {

                        isDataFromDB = true;
                        setRelevantIconToButton();
                    }
                });

            }
        });
    }

    private void setRelevantIconToButton() {
        favoritesButton.setImageDrawable(isDataFromDB ? getDrawable(R.drawable.ic_favorite) : getDrawable(R.drawable.ic_favorite_border));
    }

    private void initViews() {
        posterPath = findViewById(R.id.poster_path);
        backdropPath = findViewById(R.id.backdrop_path);
        title = findViewById(R.id.title);
        released = findViewById(R.id.released);
        language = findViewById(R.id.language);
        overview = findViewById(R.id.overview);
        votes = findViewById(R.id.votes);
        rating = findViewById(R.id.rating);
        popularity = findViewById(R.id.popularity);
        ratingBar = findViewById(R.id.rating_bar);
        favoritesButton = findViewById(R.id.ic_favorites);
    }

    private void showMovie() {
        Glide
                .with(App.getsInstance().getApplicationContext())
                .load(Const.BASE_IMAGE_URL + theMovieDetails.getPosterPath())
                .into(posterPath);
        Glide
                .with(App.getsInstance().getApplicationContext())
                .load(Const.BASE_IMAGE_URL + theMovieDetails.getBackdropPath())
                .into(backdropPath);
        title.setText(theMovieDetails.getTitle());
        released.setText(theMovieDetails.getReleaseDate());
        language.setText(theMovieDetails.getOriginalLanguage());
        overview.setText(theMovieDetails.getOverview());
        votes.setText("" + theMovieDetails.getVoteCount());
        rating.setText("" + theMovieDetails.getVoteAverage());
        popularity.setText("" + theMovieDetails.getPopularity());
        ratingBar.setRating((int)Math.round(theMovieDetails.getVoteAverage()));
    }
}
