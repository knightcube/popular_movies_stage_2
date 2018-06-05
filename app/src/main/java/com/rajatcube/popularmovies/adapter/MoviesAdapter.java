package com.rajatcube.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rajatcube.popularmovies.utils.Constants;
import com.rajatcube.popularmovies.R;
import com.rajatcube.popularmovies.model.Movies;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rajat Kumar Gupta on 01-03-2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Movies> moviesList;
    private Context context;
    private final ListItemClickListener mOnClickListener;
    public MoviesAdapter(List<Movies> moviesList,Context context,ListItemClickListener mOnClickListener) {
        this.moviesList = moviesList;
        this.context = context;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.movie_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    public interface ListItemClickListener{
         void onListItemClick(int clickedItemIndex,List<Movies> moviesList);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Movies currentMovie = moviesList.get(position);
        String actualMoviePosterPath = Constants.IMAGE_BASE_URL+Constants.SMALL_IMG_SIZE+currentMovie.getPosterPath();
        Picasso.with(context).load(actualMoviePosterPath).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).into(holder.moviePoster);
        Log.i("TAG", "onBindViewHolder: "+currentMovie.getPosterPath());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView moviePoster;
        public MyViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.movie_poster_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition,moviesList);
        }
    }
}
