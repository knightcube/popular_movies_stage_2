package com.rajatcube.popularmovies.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.rajatcube.popularmovies.model.Movies;

import java.util.List;

/**
 * Created by Rajat Kumar Gupta on 07/06/2018.
 */
public class MoviesViewModel extends AndroidViewModel {

    private LiveData<List<Movies>> moviesList;

    public LiveData<List<Movies>> getMoviesList() {
        return moviesList;
    }

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        MoviesRoomDatabase database = MoviesRoomDatabase.getDatabaseInstance(this.getApplication());
        moviesList = database.moviesDAO().getAllMovies();
    }
}
