package com.movie.app.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movie.app.R;
import com.movie.app.activities.MainActivity;
import com.movie.app.customView.MovieListViewHolder;
import com.movie.app.model.parsingModel.MovieListDataModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListViewHolder> {
    Activity activity;
    MovieListViewHolder movieListViewHolder;
    MainActivity mainActivity;
    List<MovieListDataModel> movieListDataModels;

    public MovieListAdapter(Activity activity,
                            MainActivity mainActivity,
                            List<MovieListDataModel> movieListDataModels) {
        this.activity = activity;
        this.mainActivity = mainActivity;
        this.movieListDataModels = movieListDataModels;

    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);
        movieListViewHolder = new MovieListViewHolder(view);
        return movieListViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, final int position) {

        holder.tvItemName.setText(movieListDataModels.get(position).getMovieName());
        holder.tvItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.callDetailPage(
                        "http://image.tmdb.org/t/p/w342/" + movieListDataModels.get(position).getImagePath(),
                        movieListDataModels.get(position).getUserRating(),
                        movieListDataModels.get(position).getMovieName(),
                        movieListDataModels.get(position).getDescription(),
                        movieListDataModels.get(position).getReleaseDate());
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieListDataModels.size();
    }

}
