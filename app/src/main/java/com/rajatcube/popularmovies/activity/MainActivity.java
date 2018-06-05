package com.rajatcube.popularmovies.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.facebook.stetho.Stetho;
import com.rajatcube.popularmovies.MoviePosterLoader;
import com.rajatcube.popularmovies.R;
import com.rajatcube.popularmovies.adapter.MoviesAdapter;
import com.rajatcube.popularmovies.database.MoviesRoomDatabase;
import com.rajatcube.popularmovies.model.Movies;
import com.rajatcube.popularmovies.utils.QueryUtils;

import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movies>>, MoviesAdapter.ListItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<Movies> listOfMovies;
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
//    private MoviesRoomDatabase moviesRoomDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Stetho.initializeWithDefaults(this);
        recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        AndroidNetworking.initialize(getApplicationContext());
        getLoaderManager().initLoader(1, null, MainActivity.this);
//        moviesRoomDatabase = MoviesRoomDatabase.getDatabaseInstance(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (moviesRoomDatabase.moviesDAO().getAllMovies().size() > 0) {
//            Log.i(TAG, "onResume:title " + moviesRoomDatabase.moviesDAO().getAllMovies().get(0).getTitle());
//            Log.i(TAG, "onResume:id " + moviesRoomDatabase.moviesDAO().getAllMovies().get(0).getId());
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_popular) {
            QueryUtils.filter = 0;
            getLoaderManager().restartLoader(1, null, MainActivity.this);
        }
        if (id == R.id.action_top_rated) {
            QueryUtils.filter = 1;
            getLoaderManager().restartLoader(1, null, MainActivity.this);
        }
        if (id == R.id.action_favourites) {
            Intent intent = new Intent(MainActivity.this,FavouriteMoviesActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Movies>> onCreateLoader(int i, Bundle bundle) {
        return new MoviePosterLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(Loader<List<Movies>> loader, List<Movies> moviesList) {

        if (moviesList != null) {
            this.listOfMovies = moviesList;
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MainActivity.this, 2);
            recyclerView.setLayoutManager(mLayoutManager);
            moviesAdapter = new MoviesAdapter(moviesList, MainActivity.this, MainActivity.this);
            recyclerView.setAdapter(moviesAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {

    }

    @Override
    public void onListItemClick(int clickedItemIndex, List<Movies> moviesList) {
        Intent intent = new Intent(MainActivity.this, MovieDetails.class);
        intent.putExtra("clicked_movie", moviesList.get(clickedItemIndex));
        startActivity(intent);
    }
}
