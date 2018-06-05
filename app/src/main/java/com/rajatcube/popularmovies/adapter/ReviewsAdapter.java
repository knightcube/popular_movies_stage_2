package com.rajatcube.popularmovies.adapter;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajatcube.popularmovies.R;
import com.rajatcube.popularmovies.model.ReviewResult;

import java.util.List;

/**
 * Created by Rajat Kumar Gupta on 05/06/2018.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.MyViewHolder> {

    private Context context;
    private List<ReviewResult> reviewResultList;
    public ReviewsAdapter(List<ReviewResult> reviewResultList, Context context) {
        this.reviewResultList = reviewResultList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.review_list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ReviewResult reviewResult = reviewResultList.get(position);
        holder.reviewAuthorTv.setText(reviewResult.getAuthor());
        holder.reviewContent.setText(reviewResult.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewResultList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView reviewAuthorTv;
        public TextView reviewContent;
        public MyViewHolder(View itemView) {
            super(itemView);
            reviewAuthorTv = itemView.findViewById(R.id.review_author_txt);
            reviewContent = itemView.findViewById(R.id.review_content_txt);
        }
    }
}
