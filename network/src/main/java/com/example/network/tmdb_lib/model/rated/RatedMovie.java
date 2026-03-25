package com.example.network.tmdb_lib.model.rated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.Movie;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RatedMovie extends Movie {
    @JsonProperty("rating")
    private Double rating;
}
