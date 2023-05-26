package com.example.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetail extends AppCompatActivity {

    private ImageView ivMovieBackdrops;
    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);



        ivMovieBackdrops = findViewById(R.id.ivMovieBackdrop);
        tvMovieTitle = findViewById(R.id.tvMovieTitle);
        tvMovieReleaseDate = findViewById(R.id.tvMovieReleaseDate);
        tvMovieOverview = findViewById(R.id.tvMovieOverview);

        Intent intent = getIntent();
        if (intent.hasExtra("movie")) {
            MovieModel movie = intent.getParcelableExtra("movie");

            tvMovieTitle.setText(movie.getMovieName());
            tvMovieReleaseDate.setText(movie.getMovieReleaseDate());
            tvMovieOverview.setText(movie.getMovieOverview());

            String posterUrl = "https://image.tmdb.org/t/p/w500" + movie.getMovieBackdrops();
            Glide.with(MovieDetail.this).load(posterUrl).into(ivMovieBackdrops);
        }

}
}