package com.rajatcube.popularmovies.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rajatcube.popularmovies.R;
import com.rajatcube.popularmovies.activity.MovieDetails;
import com.rajatcube.popularmovies.adapter.ReviewsAdapter;
import com.rajatcube.popularmovies.model.ReviewResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends BottomSheetDialogFragment {

    private RecyclerView reviewsRecyclerView;
    private ReviewsAdapter reviewsAdapter;
    private String reviewUrl;
    private List<ReviewResult> reviewResultList;
    private ImageView reviewsEmptyImageView;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_reviews, container, false);
        reviewsRecyclerView = rootView.findViewById(R.id.reviews_recycler_view);
        reviewsEmptyImageView = rootView.findViewById(R.id.empty_state_image);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reviewUrl = getArguments().getString("REVIEW_URL");
        fetchReviews();
        return rootView;
    }

    private void fetchReviews() {
        reviewResultList = new ArrayList<>();
        AndroidNetworking.get(reviewUrl)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray reviewsJsonArray = response.getJSONArray("results");
                            if(reviewsJsonArray.length()>0) {
                                reviewsEmptyImageView.setVisibility(View.GONE);
                                for (int i = 0; i < reviewsJsonArray.length(); i++) {
                                    JSONObject reviewJsonObject = reviewsJsonArray.getJSONObject(i);
                                    String reviewJsonAuthor = reviewJsonObject.getString("author");
                                    String reviewJsonContent = reviewJsonObject.getString("content");
                                    reviewResultList.add(new ReviewResult(reviewJsonAuthor, reviewJsonContent));
                                }
                                reviewsAdapter = new ReviewsAdapter(reviewResultList, getContext());
                                reviewsRecyclerView.setAdapter(reviewsAdapter);
                            }else{
                                reviewsEmptyImageView.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getContext(), anError.getErrorDetail() + ":Server error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
