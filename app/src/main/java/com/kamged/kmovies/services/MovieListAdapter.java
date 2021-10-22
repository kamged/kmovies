package com.kamged.kmovies.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.lifecycle.LiveData;

import com.kamged.kmovies.R;
import com.kamged.kmovies.database.AppDB;
import com.kamged.kmovies.models.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MovieListAdapter extends BaseAdapter {

    private List<Movie> _movieList = Collections.emptyList();

    public MovieListAdapter() {

    }

    @Override
    public int getCount() {
        return _movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);

        Movie m = _movieList.get(position);

        TextView tvMT = convertView.findViewById(R.id.tvMovieTitle);
        TextView tvMY = convertView.findViewById(R.id.tvMovieYear);
        TextView tvMI = convertView.findViewById(R.id.tvMovieID);

        tvMT.setText(m.get_title());
        tvMY.setText(m.get_year());
        tvMI.setText(m.getUid() > 0 ? String.valueOf(m.getUid()) : "");

        return convertView;
    }

    public void setData(List<Movie> movieList)
    {
        this._movieList = movieList;

        notifyDataSetChanged();
    }

    public void setNoData(boolean status)
    {
        this._movieList = new ArrayList<Movie>();
        Movie m = new Movie();

        if(!status)
            m.set_title("- No entries -");
        else
            m.set_title("- No movie found -");

        this._movieList.add(m);

        notifyDataSetChanged();
    }


}
