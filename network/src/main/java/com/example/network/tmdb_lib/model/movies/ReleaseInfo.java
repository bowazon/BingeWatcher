package com.example.network.tmdb_lib.model.movies;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.AbstractJsonMapping;
import com.example.network.tmdb_lib.model.movies.ReleaseDate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReleaseInfo extends AbstractJsonMapping {
    @JsonProperty("iso_3166_1")
    private String iso31661;

    @JsonProperty("release_dates")
    private List<ReleaseDate> releaseDates = new ArrayList<>();
}
