package com.example.network.tmdb_lib.model.movies;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.AbstractJsonMapping;
import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
public class Data extends AbstractJsonMapping {
    @JsonProperty("title")
    private String title;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("homepage")
    private String homepage;

    @JsonProperty("runtime")
    private Integer runtime;

    @JsonProperty("tagline")
    private String tagline;
}
