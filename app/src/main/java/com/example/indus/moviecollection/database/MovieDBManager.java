package com.example.indus.moviecollection.database;

import com.example.indus.moviecollection.App;
import com.example.indus.moviecollection.model.TheMovieDetails;
import com.example.indus.moviecollection.utils.AppExecutors;

import java.util.List;

public class MovieDBManager {

    private MovieDatabase movieDatabase = App.getsInstance().getMovieDataBase();
    private TheMovieDao theMovieDao = movieDatabase.theMovieDao();
    private AppExecutors executors;
    private static MovieDBManager instance;

    private MovieDBManager() {
        executors = new AppExecutors();
    }

    public static MovieDBManager getInstance() {
        if (instance == null) {
            instance = new MovieDBManager();
        }
        return instance;
    }

    public void insertMovie(final TheMovieDetails movie, final DatabaseCallback dataLoaded) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                theMovieDao.insert(movie);
                executors.getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        dataLoaded.onActionSuccess();
                    }
                });
            }
        };
        executors.getDbExecutor().execute(runnable);
    }

    public void updateMovie(final TheMovieDetails movie) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                theMovieDao.update(movie);
            }
        };
        executors.getDbExecutor().execute(runnable);
    }

    public void deleteMovie(final TheMovieDetails movie, final DatabaseCallback dataLoaded) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                theMovieDao.delete(movie);
                executors.getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        dataLoaded.onActionSuccess();
                    }
                });
            }
        };
        executors.getDbExecutor().execute(runnable);
    }

    public void getMovieById(final long id, final DatabaseCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final TheMovieDetails theMovieDetails = theMovieDao.getById(id);
                executors.getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDataLoaded(theMovieDetails);
                    }
                });
            }
        };
        executors.getDbExecutor().execute(runnable);
    }

    public void getAllMovies(final DatabaseCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<TheMovieDetails> theMovieDetailsData = theMovieDao.getAll();
                executors.getMainThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onDataLoaded(theMovieDetailsData);
                    }
                });
            }
        };
        executors.getDbExecutor().execute(runnable);
    }
}
