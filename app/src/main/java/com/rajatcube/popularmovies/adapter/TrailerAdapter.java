package com.rajatcube.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajatcube.popularmovies.R;
import com.rajatcube.popularmovies.fragments.TrailerBottomSheetFragment;
import com.rajatcube.popularmovies.model.Movies;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Rajat Kumar Gupta on 05/06/2018.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {

    private List<String> trailerList;
    private Context context;
    private final TrailerItemClickListener mOnClickListener;
    public TrailerAdapter(Context context, List<String> trailerList,TrailerItemClickListener mOnClickListener){
        this.trailerList = trailerList;
        this.context = context;
        this.mOnClickListener = mOnClickListener;
    }

    public interface TrailerItemClickListener{
        void onTrailerClick(int clickedItemIndex,List<String> trailerList);
    }

    @Override
    public TrailerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.trailer_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.MyViewHolder holder, int position) {
        holder.trailerText.setText(context.getString(R.string.play_trailer)+(position+1));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView trailerText;
        public MyViewHolder(View itemView) {
            super(itemView);
             trailerText = itemView.findViewById(R.id.trailer_title);
             itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onTrailerClick(clickedPosition,trailerList);
        }
    }
}
