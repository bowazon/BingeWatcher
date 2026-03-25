package com.example.network.tmdb_lib.model.movies;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.IdElement;
import com.example.network.tmdb_lib.model.core.image.Artwork;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Images extends IdElement {
    @JsonProperty("backdrops")
    private List<Artwork> backdrops = new ArrayList<>();

    @JsonProperty("logos")
    private List<Artwork> logos = new ArrayList<>();

    @JsonProperty("posters")
    private List<Artwork> posters = new ArrayList<>();
}
