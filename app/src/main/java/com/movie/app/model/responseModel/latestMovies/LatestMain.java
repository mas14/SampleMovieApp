package com.movie.app.model.responseModel.latestMovies;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.movie.app.model.responseModel.latestMovies.result.LatestResult;

import java.util.List;


@JsonObject
public class LatestMain {
    @JsonField(name = "page")
    private Integer page;
    @JsonField(name = "results")
    private List<LatestResult> results = null;
    @JsonField(name = "total_results")
    private Integer totalResults;
    @JsonField(name = "total_pages")
    private Integer totalPages;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<LatestResult> getResults() {
        return results;
    }

    public void setResults(List<LatestResult> results) {
        this.results = results;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

}
