package com.rajatcube.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajatcube.popularmovies.R;
import com.rajatcube.popularmovies.fragments.TrailerBottomSheetFragment;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Rajat Kumar Gupta on 05/06/2018.
 */
public class TrailerAdapter extends BaseAdapter {

    private List<String> trailerList;
    private Context context;
    private static LayoutInflater inflater;
    public TrailerAdapter(Context context, List<String> trailerList){
        this.trailerList = trailerList;
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return trailerList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(convertView==null)
            view = inflater.inflate(R.layout.trailer_list_item, null);
        TextView title = (TextView)view.findViewById(R.id.trailer_title); // title
        // Setting all values in listview
        for(int i = 0;i<trailerList.size();i++){
            title.setText("Play Trailer "+(i+1));
        }

        return view;
    }
}
