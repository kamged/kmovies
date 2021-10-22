package com.kamged.kmovies.services;

import android.util.Log;

import com.kamged.kmovies.models.Movie;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieService {
    private static MovieService instance = null;
    private Movie _createdMovie;

    /*public List<Movie> get_movieList() {
        return _movieList;
    }
    private List<Movie> _movieList;*/

    private  MovieService()
    {
        //_movieList = new ArrayList<Movie>();
        _createdMovie = new Movie();
    }

    public static MovieService getInstance() {
        if (instance == null) {
            instance = new MovieService();
        }

        return instance;
    }

    public boolean CreateMovie(JSONObject jsonResponse)
    {
        try {
            if (jsonResponse.getString("Response").equals("False")) {
                return false;
            }

            _createdMovie.set_title(jsonResponse.getString("Title"));
            _createdMovie.set_year(jsonResponse.getString("Year"));
            _createdMovie.set_runtime(jsonResponse.getString("Runtime"));
            _createdMovie.set_coverUrl(jsonResponse.getString("Poster"));

            //get_movieList().add(m);

            return true;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public Movie GetCreatedMovie()
    {
        // Returns the last created movie
        return _createdMovie; //get_movieList().get(_movieList.size()-1);
    }
}
