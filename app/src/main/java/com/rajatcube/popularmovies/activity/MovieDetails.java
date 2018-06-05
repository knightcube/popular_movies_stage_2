package com.rajatcube.popularmovies.activity;

import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.rajatcube.popularmovies.R;
import com.rajatcube.popularmovies.adapter.TrailerAdapter;
import com.rajatcube.popularmovies.database.MoviesRoomDatabase;
import com.rajatcube.popularmovies.fragments.ReviewsFragment;
import com.rajatcube.popularmovies.fragments.TrailerBottomSheetFragment;
import com.rajatcube.popularmovies.model.Movies;
import com.rajatcube.popularmovies.model.ReviewResult;
import com.rajatcube.popularmovies.utils.Constants;
import com.rajatcube.popularmovies.utils.QueryUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class MovieDetails extends AppCompatActivity {

    private static final String TAG = MovieDetails.class.getSimpleName();
    private String trailer_url;
    private MoviesRoomDatabase moviesRoomDatabase;
    private Movies selectedMovie;
    private List<String> trailerLinks;
    private TrailerAdapter trailerAdapter;
    private TextView movieTrailer;
    private Button reviewBtn;
    private String review_url;
    private String youtube_trailer_key;

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
        movieTrailer = findViewById(R.id.movie_detail_play_trailer_tv);
        reviewBtn = findViewById(R.id.review_btn);
        moviesRoomDatabase = MoviesRoomDatabase.getDatabaseInstance(getApplicationContext());
        selectedMovie = (Movies) getIntent().getSerializableExtra("clicked_movie");
        trailer_url = Constants.BASE_URL + "/" + selectedMovie.getId() + "/videos?api_key=" + Constants.API_KEY + "&language=en-US";
        review_url = Constants.BASE_URL +"/" +selectedMovie.getId()+"/reviews?api_key="+Constants.API_KEY+"&language=en-US";
        String actualMoviePosterPath = Constants.IMAGE_BASE_URL + Constants.SMALL_IMG_SIZE + selectedMovie.getPosterPath();
        Picasso.with(MovieDetails.this).load(actualMoviePosterPath).into(moviePosterImage);
        movieTitle.setText(selectedMovie.getTitle());
        movieOverview.setText(selectedMovie.getOverview());
        movieReleaseDate.setText(selectedMovie.getReleaseDate());
        movieVoteAverage.setText(String.valueOf(selectedMovie.getVoteAverage()));
        fetchTrailers();
        if (selectedMovie.isSaved()) {
            movieAddToFavouritesBtn.setText("Unsave");
        } else {
            movieAddToFavouritesBtn.setText("Save");
        }
        movieTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trailerLinks.size() > 1) {
                    showTrailerSheetFragment();
                }
                else if(trailerLinks.size()==0){
                    Toast.makeText(MovieDetails.this, "There are no trailers for this movie currently.", Toast.LENGTH_SHORT).show();
                }
                else {
                    playTrailer(Uri.parse(trailerLinks.get(0)));
                }
            }
        });
        movieAddToFavouritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedMovie.isSaved()) {
                    movieAddToFavouritesBtn.setText("Unsave");
                    addMovieToFavourites();
                } else {
                    movieAddToFavouritesBtn.setText("Save");
                    removeMovieFromFavourites();
                }
            }
        });
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReviewSheetFragment();
            }
        });
    }

    private void showReviewSheetFragment() {
        ReviewsFragment reviewsBottomSheetFragment = new ReviewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("REVIEW_URL",review_url);
        reviewsBottomSheetFragment.setArguments(bundle);
        reviewsBottomSheetFragment.show(getSupportFragmentManager(), reviewsBottomSheetFragment.getTag());
    }

    private void showTrailerSheetFragment() {
        TrailerBottomSheetFragment bottomSheetFragment = new TrailerBottomSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("TRAILER_LINKS", (ArrayList<String>) trailerLinks);
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }
    private void fetchTrailers() {
        trailerLinks = new ArrayList<>();
        AndroidNetworking.get(trailer_url)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            youtube_trailer_key = response.getJSONArray("results").getJSONObject(0).getString("key");
                            trailerLinks.add(Constants.YOUTUBE_BASE_URL + youtube_trailer_key);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MovieDetails.this, anError.getErrorDetail() + ":Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void playTrailer(Uri parseTrailer) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, parseTrailer);
        startActivity(browserIntent);
    }

    private void removeMovieFromFavourites() {
        moviesRoomDatabase.moviesDAO().deleteMovieWithId(selectedMovie.getId());
        selectedMovie.setSaved(false);
        Toast.makeText(this, selectedMovie.getTitle() + " is removed from favourites", Toast.LENGTH_LONG).show();
    }

    private void addMovieToFavourites() {
        selectedMovie.setSaved(true);
        moviesRoomDatabase.moviesDAO().insert(selectedMovie);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Share trailer");
            i.putExtra(Intent.EXTRA_TEXT, trailerLinks.get(0));
            startActivity(Intent.createChooser(i, "Choose"));
        }
        return super.onOptionsItemSelected(item);
    }
}

