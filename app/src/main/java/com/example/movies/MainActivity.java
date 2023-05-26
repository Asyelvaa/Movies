package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterListener{

    RecyclerView rvMovielist;
    ArrayList<MovieModel> listMovie;
    private MovieAdapter adapterMovie;
    ProgressBar progressBar;

    public void getMovies(){
        progressBar.setVisibility(View.VISIBLE);

        AndroidNetworking.initialize(getApplicationContext());
        String url = "https://api.themoviedb.org/3/movie/popular";
        AndroidNetworking.get(url)
                .addQueryParameter("api_key", "830dd38e58acd49964ad520461d7a6f8")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArrayMovies = response.getJSONArray("results");
                            for (int i = 0; i < jsonArrayMovies.length(); i++) {
                                MovieModel movie = new MovieModel();
                                JSONObject jsonTeam = jsonArrayMovies.getJSONObject(i);

                                movie.setMovieName(jsonTeam.getString("title"));
                                movie.setMovieReleaseDate(jsonTeam.getString("release_date"));
                                movie.setMoviePoster(jsonTeam.getString("poster_path"));
                                movie.setMovieOverview(jsonTeam.getString("overview"));
                                movie.setMovieBackdrops(jsonTeam.getString("backdrop_path"));
                                listMovie.add(movie);
                            }

                            adapterMovie = new MovieAdapter(getApplicationContext(), listMovie, MainActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                            rvMovielist.setHasFixedSize(true);
                            rvMovielist.setLayoutManager(mLayoutManager);
                            rvMovielist.setAdapter(adapterMovie);

                            progressBar.setVisibility(View.GONE);
                            rvMovielist.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Log.d("failed", "onErrorr: "+error.toString());
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listMovie = new ArrayList<>();
        progressBar = findViewById(R.id.progressbar);
        rvMovielist = findViewById(R.id.rvMovieList);

        listMovie = new ArrayList<>();
        progressBar =findViewById(R.id.progressbar);
        getMovies();

    }

    public void onMovieSelected(MovieModel movie) {
        Intent intent = new Intent(MainActivity.this, MovieDetail.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }

}