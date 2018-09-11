package com.movie.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;
import com.movie.app.R;
import com.movie.app.adapter.MovieListAdapter;
import com.movie.app.constants.Constants;
import com.movie.app.model.parsingModel.MovieListDataModel;
import com.movie.app.model.responseModel.latestMovies.LatestMain;
import com.movie.app.service.RestAPI;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RestAPI moviesAPICall;
    Map<String, String> apiKey = new HashMap<String, String>();
    Map<String, String> pageNumber = new HashMap<String, String>();

    List<MovieListDataModel> movieListDataModels = new ArrayList<MovieListDataModel>();

    MovieListAdapter movieListAdapter;

    Integer pageCount = 0;
    LinearLayoutManager mLayoutManager;
    RecyclerView movieList;
    SwipyRefreshLayout swipeRefreshLayout;
    Button filterBtnLatest;
    Button filterBtnOldest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(LoganSquareConverterFactory.create())
                .build();
        moviesAPICall = retrofit.create(RestAPI.class);

        filterBtnOldest = (Button) findViewById(R.id.filterBtnOldest);
        filterBtnLatest = (Button) findViewById(R.id.filterBtnLatest);

        movieList = (RecyclerView) findViewById(R.id.movie_list);
        mLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        movieList.setLayoutManager(mLayoutManager);

        swipeRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                loadMore();
            }
        });

        filterBtnOldest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movieListDataModels != null) {
                    if(movieListDataModels.size()>0){
                        Collections.sort(movieListDataModels, new Comparator<MovieListDataModel>() {
                            @Override
                            public int compare(MovieListDataModel movieListDataModel, MovieListDataModel t1) {
                                //app queries the /movie/popular
                                return movieListDataModel.getReleaseDate().compareTo(t1.getReleaseDate());
                            }
                        });
                        movieListAdapter.notifyDataSetChanged();
                    }
                }

            }
        });

        filterBtnLatest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movieListDataModels != null) {
                    if(movieListDataModels.size()>0){
                        Collections.sort(movieListDataModels, new Comparator<MovieListDataModel>() {
                            @Override
                            public int compare(MovieListDataModel movieListDataModel, MovieListDataModel t1) {
                                //app queries the /movie/popular
                                return movieListDataModel.getReleaseDate().compareTo(t1.getReleaseDate());
                            }
                        });
                        Collections.reverse(movieListDataModels);
                        movieListAdapter.notifyDataSetChanged();
                    }
                }

            }
        });
        retrieveMovies();

    }

    private void retrieveMovies() {
        pageCount = pageCount + 1;
        apiKey.put("api_key", "8f2b52cb3578ed865acfbd4d642dc062");
        pageNumber.put("page", String.valueOf(pageCount));

        Call<LatestMain> getLatestMovies = moviesAPICall.getMovies(apiKey, pageNumber);
        getLatestMovies.enqueue(new Callback<LatestMain>() {
            @Override
            public void onResponse(Call<LatestMain> call, Response<LatestMain> response) {
                if (response.isSuccess()) {
                    if (response.body().getResults().size() != 0 &&
                            response.body().getResults() != null) {
                        for (int i = 0; i < response.body().getResults().size(); i++) {
                            movieListDataModels.add(new MovieListDataModel(
                                    response.body().getResults().get(i).getPosterPath(),
                                    response.body().getResults().get(i).getOriginalTitle(),
                                    response.body().getResults().get(i).getOverview(),
                                    response.body().getResults().get(i).getVoteAverage(),
                                    response.body().getResults().get(i).getReleaseDate(),
                                    response.body().getResults().get(i).getPopularity()));
                        }
                        movieListAdapter = new MovieListAdapter(
                                MainActivity.this, MainActivity.this,
                                movieListDataModels);
                        movieList.setAdapter(movieListAdapter);
                    } else {
                        Toast.makeText(MainActivity.this, "no data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "no data available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LatestMain> call, Throwable t) {
                Toast.makeText(MainActivity.this, "no data available", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void loadMore() {

        pageCount = pageCount + 1;
        apiKey.put("api_key", "8f2b52cb3578ed865acfbd4d642dc062");
        pageNumber.put("page", String.valueOf(pageCount));

        Call<LatestMain> getLatestMovies = moviesAPICall.getMovies(apiKey, pageNumber);
        getLatestMovies.enqueue(new Callback<LatestMain>() {
            @Override
            public void onResponse(Call<LatestMain> call, Response<LatestMain> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccess()) {
                    if (response.body().getResults().size() != 0 &&
                            response.body().getResults() != null) {
                        for (int i = 0; i < response.body().getResults().size(); i++) {

                            movieListDataModels.add(new MovieListDataModel(
                                    response.body().getResults().get(i).getPosterPath(),
                                    response.body().getResults().get(i).getOriginalTitle(),
                                    response.body().getResults().get(i).getOverview(),
                                    response.body().getResults().get(i).getVoteAverage(),
                                    response.body().getResults().get(i).getReleaseDate(),
                                    response.body().getResults().get(i).getPopularity()));
                        }
                        movieListAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MainActivity.this, "no data available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "no data available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LatestMain> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "no data available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callDetailPage(String imagePath, Double userRating, String movieName,
                               String description, String releaseDate) {
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra("image_name", imagePath);
        intent.putExtra("userRating", userRating);
        intent.putExtra("movieName", movieName);
        intent.putExtra("description", description);
        intent.putExtra("releaseDate", releaseDate);
        startActivity(intent);
    }
}
