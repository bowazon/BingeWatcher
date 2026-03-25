package com.example.network.tmdb_lib.model.movielists;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.Movie;
import com.example.network.tmdb_lib.model.core.ResultsPage;
import com.example.network.tmdb_lib.model.movielists.Dates;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovieResultsPageWithDates extends ResultsPage<Movie> {
    @JsonProperty("dates")
    private Dates dates;
}
