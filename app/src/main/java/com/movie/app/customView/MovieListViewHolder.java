package com.movie.app.customView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.movie.app.R;

public class MovieListViewHolder extends RecyclerView.ViewHolder {
    public TextView tvItemName;

    public MovieListViewHolder(View itemView) {
        super(itemView);
        tvItemName = (TextView) itemView.findViewById(R.id.tvItemName);

    }
}
