package com.rajatcube.popularmovies.activity;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajatcube.popularmovies.R;
import com.rajatcube.popularmovies.model.Movies;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView moviePosterImage = (ImageView) findViewById(R.id.movie_detail_poster_image);
        TextView movieTitle = (TextView) findViewById(R.id.movie_detail_title);
        TextView movieOverview = (TextView) findViewById(R.id.movie_detail_plot_synopsis);
        TextView movieReleaseDate = (TextView) findViewById(R.id.movie_detail_release_date);
        TextView movieVoteAverage = (TextView) findViewById(R.id.movie_detail_vote_average);

        Movies selectedMovie = (Movies) getIntent().getSerializableExtra("clicked_movie");

        Picasso.with(MovieDetails.this).load(selectedMovie.getPosterPath()).into(moviePosterImage);
        movieTitle.setText(selectedMovie.getTitle());
        movieOverview.setText(selectedMovie.getOverview());
        try {
            movieReleaseDate.setText(selectedMovie.getReleaseDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        movieVoteAverage.setText(String.valueOf(selectedMovie.getVoteAverage()));
    }
}
