package com.rajatcube.popularmovies.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.rajatcube.popularmovies.R;
import com.rajatcube.popularmovies.adapter.TrailerAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrailerBottomSheetFragment extends BottomSheetDialogFragment implements TrailerAdapter.TrailerItemClickListener{

    private List<String> trailerLinks;

    public TrailerBottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_trailer_bottom_sheet, container, false);
        RecyclerView trailerRecyclerView = rootView.findViewById(R.id.trailer_recycler_view);
        ImageView emptyResultsIv = rootView.findViewById(R.id.empty_state_image);
        if (getArguments() != null) {
            trailerLinks = getArguments().getStringArrayList("TRAILER_LINKS");
            if (trailerLinks.size() == 0) {
                emptyResultsIv.setVisibility(View.VISIBLE);
            } else {
                emptyResultsIv.setVisibility(View.GONE);
            }
        }
        trailerRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        TrailerAdapter trailerAdapter = new TrailerAdapter(getContext(), trailerLinks, this);
        trailerRecyclerView.setAdapter(trailerAdapter);
        return rootView;
    }
    private void playTrailer(Uri parseTrailer) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, parseTrailer);
        startActivity(browserIntent);
    }

    @Override
    public void onTrailerClick(int clickedItemIndex, List<String> trailerList) {
        playTrailer(Uri.parse(trailerLinks.get(clickedItemIndex)));
    }
}
