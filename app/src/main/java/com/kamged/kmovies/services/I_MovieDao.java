package com.kamged.kmovies.services;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.kamged.kmovies.models.Movie;

import java.util.List;

@Dao
public interface I_MovieDao {

    @Insert
    void createMovie(Movie m);

    @Query("SELECT * FROM movies ORDER BY _uid ASC")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :val || '%'")
    // List<Movie> getMoviesByTitle(String val);
    LiveData<List<Movie>> getMoviesByTitle(String val);
}
