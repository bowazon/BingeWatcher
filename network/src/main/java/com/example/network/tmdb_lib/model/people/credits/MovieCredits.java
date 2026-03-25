package com.example.network.tmdb_lib.model.people.credits;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.IdElement;
import com.example.network.tmdb_lib.model.people.credits.MovieCast;
import com.example.network.tmdb_lib.model.people.credits.MovieCrew;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovieCredits extends IdElement {
    @JsonProperty("cast")
    private List<MovieCast> cast = new ArrayList<>();

    @JsonProperty("crew")
    private List<MovieCrew> crew = new ArrayList<>();
}
