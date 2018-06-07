package com.rajatcube.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.rajatcube.popularmovies.model.Movies;
import com.rajatcube.popularmovies.utils.QueryUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Rajat Kumar Gupta on 28-02-2018.
 */

public class MoviePosterLoader extends AsyncTaskLoader<List<Movies>>  {
    private List<Movies> listOfMovies;
    public MoviePosterLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movies> loadInBackground() {
        try {
            URL url = QueryUtils.createUrl();
            String jsonResponse = QueryUtils.makeHttpRequest(url);
            listOfMovies = QueryUtils.parseJson(jsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listOfMovies;
    }

}
