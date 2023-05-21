package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MainActivity extends AppCompatActivity {

    RecyclerView rvMovielist;
    ArrayList<MovieModel> listMovie;
    private MovieAdapter adapterMovie;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ProgressBar progressBar = findViewById(R.id.progressbar);
        String url = "https://api.themoviedb.org/830dd38e58acd49964ad520461d7a6f8";
        AndroidNetworking.get(url)
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject success) {
                        try {
                            JSONArray jsonArrayMovies = success.getJSONArray("");
                            for (int i = 0; i < jsonArrayMovies.length(); i++) {
                                MovieModel movie = new MovieModel();
                                JSONObject jsonTeam = jsonArrayMovies.getJSONObject(i);
                                movie.setMovieName(jsonTeam.getString(""));
                                movie.setMovieDetail(jsonTeam.getString(""));
                                movie.setMoviePoster(jsonTeam.getString(""));
                                listMovie.add(movie);
                            }

//                            listMovie = findViewById(R.id.rvMovieList);
//                            adapterMovie = new MovieAdapter(getApplicationContext(), listMovie,MainActivity.this);
//                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//                            rvMovielist.setHasFixedSize(true);
//                            rvMovielist.setLayoutManager(mLayoutManager);
//                            rvMovielist.setAdapter(MovieAdapter);

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
}