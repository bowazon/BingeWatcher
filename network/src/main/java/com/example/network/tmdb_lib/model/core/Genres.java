package com.example.network.tmdb_lib.model.core;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.example.network.tmdb_lib.model.core.AbstractJsonMapping;
import com.example.network.tmdb_lib.model.core.Genre;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Genres extends AbstractJsonMapping {
    @JsonProperty("genres")
    private List<Genre> genres = new ArrayList<>();
}
