package com.example.movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterListener {

    RecyclerView rvMovieList;
    ArrayList<MovieModel> movieModelList;
    MovieAdapter movieAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMovieList = findViewById(R.id.rvMovieList);
        progressBar = findViewById(R.id.progressbar);

        movieModelList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movieModelList, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvMovieList.setLayoutManager(layoutManager);
        rvMovieList.setAdapter(movieAdapter);

        getMovies();
    }

    public void getMovies() {
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
                                JSONObject jsonMovie = jsonArrayMovies.getJSONObject(i);

                                MovieModel movie = new MovieModel();
                                movie.setMovieName(jsonMovie.getString("title"));
                                movie.setMovieReleaseDate(jsonMovie.getString("release_date"));
                                movie.setMoviePoster(jsonMovie.getString("poster_path"));
                                movie.setMovieOverview(jsonMovie.getString("overview"));
                                movie.setMovieBackdrops(jsonMovie.getString("backdrop_path"));
                                movie.setMovieRating(jsonMovie.getString("vote_average"));
                                movieModelList.add(movie);
                            }

                            movieAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            rvMovieList.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(MainActivity.this, "Failed to fetch movies", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                logoutUser();
                Intent intent = new Intent(MainActivity.this, LoginPage.class);
                startActivity(intent);
                finish();
        }
        return true;
    }

    private void logoutUser() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            startActivity(new Intent(MainActivity.this, LoginPage.class));
            finish();
        });
    }

    @Override
    public void onMovieSelected(MovieModel movie) {
        Intent intent = new Intent(MainActivity.this, MovieDetail.class);
        intent.putExtra("movie", movie);
        startActivity(intent);
    }


}
