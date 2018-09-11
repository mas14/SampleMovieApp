package com.movie.app.service;

import com.movie.app.constants.Constants;
import com.movie.app.model.responseModel.latestMovies.LatestMain;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface RestAPI {
    @GET(Constants.POPULAR_MOVIES)
    Call<LatestMain> getMovies(@QueryMap Map<String,String> apiKe,
                               @QueryMap Map<String,String> pageId);
}
