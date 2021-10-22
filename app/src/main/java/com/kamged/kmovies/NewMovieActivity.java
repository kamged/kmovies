package com.kamged.kmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kamged.kmovies.models.Movie;
import com.kamged.kmovies.services.I_ReqListener;
import com.kamged.kmovies.services.MovieService;
import com.kamged.kmovies.services.OMDbService;
import com.kamged.kmovies.viewmovels.MovieViewModel;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class NewMovieActivity extends AppCompatActivity {

    GridLayout glMovieDetails;
    Button btGetMovieDetails;
    EditText edMovieTitle;
    EditText edMovieYear;
    ImageView imMovieCover;
    Button btCreateMovie;
    Button btNewEntry;

    private MovieViewModel _mMovieViewModel;
    private Movie _resMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_movie);

        _mMovieViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MovieViewModel.class);

        glMovieDetails = findViewById(R.id.glMovieDetails);
        btGetMovieDetails = findViewById(R.id.btGetMovieDetails);
        edMovieTitle = findViewById(R.id.edMovieTitle);
        edMovieYear = findViewById(R.id.edMovieYear);
        imMovieCover = findViewById(R.id.img_cover);
        btCreateMovie = findViewById(R.id.btCreateMovie);
        btNewEntry = findViewById(R.id.btNewEntry);

        OMDbService.getInstance(this);
        btGetMovieDetails.setEnabled(OMDbService.getInstance().CheckKeyStatus());

        if(!OMDbService.getInstance().CheckKeyStatus())
            Toast.makeText(this, "APIKEY is missing", Toast.LENGTH_LONG).show();


        btGetMovieDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(TextUtils.isEmpty(edMovieTitle.getText()) || edMovieYear.getText().length() < 1) {
                    Toast.makeText(getApplicationContext(), "Movie title and year required", Toast.LENGTH_LONG).show();
                    return;
                }

                glMovieDetails.setVisibility(v.VISIBLE);
                imMovieCover.setVisibility(v.VISIBLE);



                OMDbService.getInstance().MakeOMDBReq(edMovieTitle.getText().toString().trim(), edMovieYear.getText().toString().trim(), new I_ReqListener<JSONObject>() {
                    @Override
                    public void getResult(JSONObject object) {
                        Log.d("mainac", object.toString());

                        try {
                            boolean status = MovieService.getInstance().CreateMovie(object);

                                if(status) {
                                    _resMovie = MovieService.getInstance().GetCreatedMovie();

                                    ((TextView) findViewById(R.id.tvOV_MovieTitle)).setText(_resMovie.get_title());
                                    ((TextView) findViewById(R.id.tvOV_MovieYear)).setText(_resMovie.get_year());
                                    ((TextView) findViewById(R.id.tvOV_MovieLength)).setText(_resMovie.get_runtime());

                                    Picasso.get().load(_resMovie.get_coverUrl()).into(imMovieCover);
                        }

                        btCreateMovie.setVisibility(v.VISIBLE);

                        } catch (Exception e) {
                            throw  new RuntimeException(e);
                        }
                    }
                });

            }

        });

        btCreateMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mMovieViewModel.CreateMovieEntry(_resMovie);

                Toast.makeText(getApplicationContext(), "Movie entry created", Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        btNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edMovieTitle.getText().clear();
                edMovieYear.getText().clear();
            }
        });
    }

}
