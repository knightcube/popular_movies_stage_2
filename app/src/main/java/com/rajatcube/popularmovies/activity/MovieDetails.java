package com.rajatcube.popularmovies.activity;

import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.rajatcube.popularmovies.R;
import com.rajatcube.popularmovies.database.MoviesRoomDatabase;
import com.rajatcube.popularmovies.model.Movies;
import com.rajatcube.popularmovies.utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

import javax.security.auth.login.LoginException;

public class MovieDetails extends AppCompatActivity {

    private static final String TAG = MovieDetails.class.getSimpleName();
    private String trailer_url;
    private MoviesRoomDatabase moviesRoomDatabase;
    private Movies selectedMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView moviePosterImage = findViewById(R.id.movie_detail_poster_image);
        final Button movieAddToFavouritesBtn = findViewById(R.id.movie_detail_favorite_btn);
        TextView movieTitle = findViewById(R.id.movie_detail_title);
        TextView movieOverview = findViewById(R.id.movie_detail_plot_synopsis);
        TextView movieReleaseDate = findViewById(R.id.movie_detail_release_date);
        TextView movieVoteAverage = findViewById(R.id.movie_detail_vote_average);
        TextView movieTrailer = findViewById(R.id.movie_detail_play_trailer_tv);
        moviesRoomDatabase = MoviesRoomDatabase.getDatabaseInstance(getApplicationContext());
        selectedMovie = (Movies) getIntent().getSerializableExtra("clicked_movie");
        trailer_url = Constants.BASE_URL + "/" + selectedMovie.getId() + "/videos?api_key=" + Constants.API_KEY + "&language=en-US";
        String actualMoviePosterPath = Constants.IMAGE_BASE_URL+Constants.SMALL_IMG_SIZE+selectedMovie.getPosterPath();
        Picasso.with(MovieDetails.this).load(actualMoviePosterPath).into(moviePosterImage);
        movieTitle.setText(selectedMovie.getTitle());
        movieOverview.setText(selectedMovie.getOverview());
        movieReleaseDate.setText(selectedMovie.getReleaseDate());
        movieVoteAverage.setText(String.valueOf(selectedMovie.getVoteAverage()));
        if(selectedMovie.isSaved()){
            movieAddToFavouritesBtn.setText("Unsave");
        }else{
            movieAddToFavouritesBtn.setText("Save");
        }
        Log.i(TAG, "stateBeforeOperation:"+selectedMovie.isSaved());
        movieTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playTrailer();
            }
        });
        movieAddToFavouritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selectedMovie.isSaved()){
                    movieAddToFavouritesBtn.setText("Unsave");
                    addMovieToFavourites();
                }else{
                    movieAddToFavouritesBtn.setText("Save");
                    removeMovieFromFavourites();
                }
            }
        });
    }

    private void removeMovieFromFavourites() {
        moviesRoomDatabase.moviesDAO().deleteMovieWithId(selectedMovie.getId());
        selectedMovie.setSaved(false);
        Toast.makeText(this, selectedMovie.getTitle()+" is removed from favourites", Toast.LENGTH_LONG).show();
    }

    private void addMovieToFavourites() {
        selectedMovie.setSaved(true);
        moviesRoomDatabase.moviesDAO().insert(selectedMovie);
    }

    private void playTrailer() {
        AndroidNetworking.get(trailer_url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String youtube_trailer_key = response.getJSONArray("results").getJSONObject(0).getString("key");
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_BASE_URL + youtube_trailer_key));
                            startActivity(browserIntent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MovieDetails.this, anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

