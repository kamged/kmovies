package com.kamged.kmovies.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;

import com.kamged.kmovies.models.Movie;
import com.kamged.kmovies.services.I_MovieDao;

import java.util.ArrayList;
import java.util.List;

public class MovieRespo {
    private I_MovieDao _movieDao;

    public MovieRespo(Application application)
    {
        _movieDao = AppDB.getInstance(application).movieDao(); //DBService.getInstance(application).GetDatabase().movieDao();
    }

    public void CreateMovie(Movie movie)
    {
        new InsertMovieAsyncTask(_movieDao).execute(movie);
    }

    public LiveData<List<Movie>> GetAllMovies()
    {
        return _movieDao.getAllMovies();
    }

    /*public LiveData<List<Movie>> GetMoviesByTitle(String val) {
        MutableLiveData<List<Movie>> tmp = new MutableLiveData<List<Movie>>();
        tmp.setValue(_movieDao.getMoviesByTitle(val));

        return tmp;
    }*/

    public LiveData<List<Movie>> GetMoviesByTitle(String val) {
        return _movieDao.getMoviesByTitle(val);
    }

    private static class InsertMovieAsyncTask extends AsyncTask<Movie, Void, Void> {
        private I_MovieDao _movieDao;

        private InsertMovieAsyncTask(I_MovieDao movieDao) {
            this._movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movie... movies) {
            _movieDao.createMovie(movies[0]);

            return null;
        }
    }
}
