package com.rajatcube.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.rajatcube.popularmovies.model.Movies;

import java.util.List;

/**
 * Created by Rajat Kumar Gupta on 02/06/2018.
 */
@Dao
public interface MoviesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movies movie);

    @Query("DELETE FROM movies_table")
    void deleteAll();

    @Query("SELECT * FROM movies_table")
    LiveData<List<Movies>> getAllMovies();

    @Query("DELETE FROM movies_table WHERE ID= :id")
    void deleteMovieWithId(int id);
}
