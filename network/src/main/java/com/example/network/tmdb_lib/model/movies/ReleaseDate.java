package com.example.network.tmdb_lib.model.movies;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.AbstractJsonMapping;
import com.example.network.tmdb_lib.model.movies.ReleaseType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReleaseDate extends AbstractJsonMapping {
    @JsonProperty("certification")
    private String certification;

    @JsonProperty("descriptors")
    private List<Object> descriptors = new ArrayList<>();

    @JsonProperty("iso_639_1")
    private String iso6391;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("note")
    private String note;

    @JsonProperty("type")
    private ReleaseType type;
}
