package com.example.movies;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MovieModel implements Parcelable {

    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }

    public String getMoviePoster() { return moviePoster; }
    public void setMoviePoster(String moviePoster) { this.moviePoster = "https://image.tmdb.org/t/p/w500" + moviePoster; }

    public String getMovieReleaseDate() { return movieReleaseDate; }
    public void setMovieReleaseDate(String movieReleaseDate) { this.movieReleaseDate = movieReleaseDate; }

    public String getMovieOverview() { return movieOverview; }
    public void setMovieOverview(String movieOverview) { this.movieOverview = movieOverview; }

    public String getMovieBackdrops() { return movieBackdrops; }
    public void setMovieBackdrops(String movieBackdrops) {this.movieBackdrops = movieBackdrops; }

    public String getMovieRating() {return movieRating;}
    public void setMovieRating(String movieRating) {this.movieRating = movieRating;}

    private String movieName;
    private String moviePoster;
    private String movieReleaseDate;
    private String movieOverview;
    private String movieBackdrops;
    private String movieRating;

    protected MovieModel(Parcel in) {
        movieName = in.readString();
        movieReleaseDate = in.readString();
        moviePoster = in.readString();
        movieOverview = in.readString();
        movieBackdrops = in.readString();
        movieRating = in.readString();
    }

    MovieModel() {

    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel parcel) { return new MovieModel(parcel);}

        @Override
        public MovieModel[] newArray(int i) { return new MovieModel[0]; }
    };

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(movieName);
        parcel.writeString(movieReleaseDate);
        parcel.writeString(moviePoster);
        parcel.writeString(movieOverview);
        parcel.writeString(movieBackdrops);
        parcel.writeString(movieRating);
    }
}
