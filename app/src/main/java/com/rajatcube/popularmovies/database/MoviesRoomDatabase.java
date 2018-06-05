package com.rajatcube.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.rajatcube.popularmovies.model.Movies;

/**
 * Created by Rajat Kumar Gupta on 02/06/2018.
 */
@Database(entities = {Movies.class},version = 1,exportSchema = false)
public abstract class MoviesRoomDatabase extends RoomDatabase{

    public abstract MoviesDAO moviesDAO();

    private static MoviesRoomDatabase INSTANCE;

    public static MoviesRoomDatabase getDatabaseInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (MoviesRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MoviesRoomDatabase.class, "movies_database")
//                            Enable temporarily to check implementation of Room
                            .allowMainThreadQueries()
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
