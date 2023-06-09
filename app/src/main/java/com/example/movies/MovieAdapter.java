package com.example.movies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
        final MovieModel movie = this.movieModelList.get(holder.getAdapterPosition());
        holder.tvMovieTitle.setText(movie.getMovieName());
        holder.tvMovieReleaseDate.setText(movie.getMovieReleaseDate());
        Glide.with(context).load(movie.getMoviePoster()).into(holder.ivMoviePoster);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMovieSelected(movie);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.inflate(R.menu.delete_menu);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                int position = holder.getAdapterPosition();
                                if (position != RecyclerView.NO_POSITION) {
                                    showConfirmationDialog(v.getContext(), position);
                                }
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMovieTitle, tvMovieReleaseDate;
        public ImageView ivMoviePoster;

        public MyViewHolder(View view) {
            super(view);
            tvMovieTitle = view.findViewById(R.id.tvMovieTitle);
            tvMovieReleaseDate = view.findViewById(R.id.tvMovieReleaseDate);
            ivMoviePoster = view.findViewById(R.id.ivMoviePoster);
        }

    }

    private void showConfirmationDialog(Context context, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Delete Item");
        builder.setMessage("Are you sure want to delete this item?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteItem(position);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteItem(int position) {
        movieModelList.remove(position);
        notifyItemRemoved(position);
    }

    public interface MovieAdapterListener {
        void onMovieSelected(MovieModel movie);
    }
}
