package com.kamged.kmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kamged.kmovies.models.Movie;
import com.kamged.kmovies.services.MovieListAdapter;
import com.kamged.kmovies.viewmovels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton mAddMovieFab;
    GridView gvMovieList;
    TextView tvNumMovies;
    EditText edSearch;
    MovieListAdapter _movieListAdapter;
    MovieViewModel _movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAddMovieFab = findViewById(R.id.add_movie_fab);
        gvMovieList = findViewById(R.id.movie_list);
        tvNumMovies = findViewById(R.id.tvNumMovies);
        edSearch = findViewById(R.id.edSearch);
        _movieListAdapter = new MovieListAdapter();
        gvMovieList.setAdapter(_movieListAdapter);
        _movieViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MovieViewModel.class);//new ViewModelProvider(this).get(MovieViewModel.class);

        _movieViewModel.GetAllMovies().observe(this, new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(List<Movie> movies) {
                        if(!movies.isEmpty()) {
                            _movieListAdapter.setData(movies);

                            tvNumMovies.setText(getString(R.string.num_movies, String.valueOf(_movieListAdapter.getCount())));
                        } else {
                            _movieListAdapter.setNoData(false);
                            Toast.makeText(MainActivity.this, "No entries found.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                mAddMovieFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentAddMovie = new Intent(MainActivity.this, NewMovieActivity.class);

                        edSearch.getText().clear();
                        startActivity(intentAddMovie);
                    }
                });

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchTerm = s.toString().trim();

                    //_movieViewModel.searchMovieByTitle(edSearch.getText().toString().trim());
                    _movieViewModel.GetMoviesByTitle(searchTerm).observe(MainActivity.this, new Observer<List<Movie>>() {
                        @Override
                        public void onChanged(List<Movie> movies) {
                            if (!movies.isEmpty())
                                _movieListAdapter.setData(movies);
                            else
                                _movieListAdapter.setNoData(true);
                        }
                    });

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
}
