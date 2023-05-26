package com.example.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private Context context;
    private List<MovieModel> movieModelList;
    private MovieAdapterListener listener;

    public MovieAdapter(Context context, List<MovieModel> movieModelList, MovieAdapterListener listener) {
        this.context = context;
        this.movieModelList = movieModelList;
        this.listener = listener;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final MovieModel movie = this.  movieModelList.get(position);
        holder.tvMovieTitle.setText(movie.getMovieName());
        holder.tvMovieReleaseDate.setText(movie.getMovieReleaseDate());
        Glide.with(context).load(movie.getMoviePoster()).into(holder.ivMoviePoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMovieSelected(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvMovieTitle, tvMovieReleaseDate;
        public ImageView ivMoviePoster;

        public MyViewHolder(View view) {
            super(view);
            tvMovieTitle = view.findViewById(R.id.tvMovieTitle);
            tvMovieReleaseDate = view.findViewById(R.id.tvMovieReleaseDate);
            ivMoviePoster = view.findViewById(R.id.ivMoviePoster);
        }
    }
    public interface MovieAdapterListener {
        void onMovieSelected(MovieModel movie);
    }
}

