package com.example.movies;

import android.graphics.Movie;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MovieModel implements Parcelable {

    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }

    public String getMoviePoster() { return moviePoster; }
    public void setMoviePoster(String moviePoster) { this.moviePoster = moviePoster; }

    public String getMovieDetail() { return movieDetail; }
    public void setMovieDetail(String movieDetail) { this.movieDetail = movieDetail; }

    private String movieName;
    private String moviePoster;
    private String movieDetail;

    protected MovieModel(Parcel in) {
        movieName = in.readString();
        movieDetail = in.readString();
        moviePoster = in.readString();
    }

    MovieModel() {

    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel parcel) { return null; }

        @Override
        public MovieModel[] newArray(int i) { return new MovieModel[0]; }
    };

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(movieName);
        parcel.writeString(movieDetail);
        parcel.writeString(moviePoster);
    }
}
