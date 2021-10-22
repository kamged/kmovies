package com.kamged.kmovies.viewmovels;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.Transformations;

import com.kamged.kmovies.database.MovieRespo;
import com.kamged.kmovies.models.Movie;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MovieRespo _movieRespo;
    //private MutableLiveData<String> query;

    public MovieViewModel(@NonNull Application application) {
        super(application);

        _movieRespo = new MovieRespo(application);
        //query = new MutableLiveData<String>();
    }

    public  void CreateMovieEntry(Movie m)
    {
        _movieRespo.CreateMovie(m);
    }

    public LiveData<List<Movie>> GetAllMovies()
    {
        return _movieRespo.GetAllMovies();
    }

    public LiveData<List<Movie>> GetMoviesByTitle(String val)
    {
        return _movieRespo.GetMoviesByTitle(val);
    }

    /*public LiveData<List<Movie>> GetMoviesByTitleResult()
    {
        return Transformations.switchMap(query, new Function<String, LiveData<List<Movie>>>() {
            @Override
            public LiveData<List<Movie>> apply(String input) {
                return _movieRespo.GetMoviesByTitle(input);
            }
        });
    }

    public void searchMovieByTitle(String val)
    {
        query.setValue(val);
    }*/
}
