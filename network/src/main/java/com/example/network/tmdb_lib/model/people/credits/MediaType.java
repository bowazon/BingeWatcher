package com.example.network.tmdb_lib.model.people.credits;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MediaType {
    @JsonProperty("movie")
    MOVIE,

    @JsonProperty("tv")
    TV
}
