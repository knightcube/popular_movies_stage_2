package com.rajatcube.popularmovies.utils;

import com.rajatcube.popularmovies.BuildConfig;

/**
 * Created by Rajat Kumar Gupta on 28-02-2018.
 */

public class Constants {
    public static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    public static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    public static final String POPULAR_MOVIES_BASE_URL = BASE_URL+"/popular?api_key="+ BuildConfig.API_KEY;
    public static final String TOP_RATED_MOVIES_BASE_URL = BASE_URL+"/top_rated?api_key="+BuildConfig.API_KEY;
//    public static final String RECOMMENDATIONS_URL = BASE_URL+"383498"+"/recommendations?api_key="+API_KEY;
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    public static final String SMALL_IMG_SIZE = "w342/";
    public static final String BIG_IMG_SIZE = "w500/";

}
