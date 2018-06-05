package com.rajatcube.popularmovies.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.rajatcube.popularmovies.R;
import com.rajatcube.popularmovies.activity.MovieDetails;
import com.rajatcube.popularmovies.adapter.TrailerAdapter;
import com.rajatcube.popularmovies.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrailerBottomSheetFragment extends BottomSheetDialogFragment {

    private List<String> trailerLinks;
    private TrailerAdapter trailerAdapter;
    public TrailerBottomSheetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_trailer_bottom_sheet, container, false);
        ListView trailerListView = rootView.findViewById(R.id.trailer_list_view);
        if (getArguments() != null) {
            trailerLinks = getArguments().getStringArrayList("TRAILER_LINKS");
        }
        trailerAdapter = new TrailerAdapter(getContext(),trailerLinks);
        trailerListView.setAdapter(trailerAdapter);
        trailerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                trailerLinks.get(position);
                playTrailer(Uri.parse(trailerLinks.get(position)));
            }
        });


        return rootView;
    }
    private void playTrailer(Uri parseTrailer) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, parseTrailer);
        startActivity(browserIntent);
    }
}
