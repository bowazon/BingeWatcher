package com.example.network.tmdb_lib.model.people.credits;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.example.network.tmdb_lib.model.people.credits.MediaType;
import com.example.network.tmdb_lib.model.people.credits.MovieCrew;
import com.example.network.tmdb_lib.model.people.credits.TvCrew;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
    @JsonSubTypes.Type(value = MovieCrew.class, name = "movie"),
    @JsonSubTypes.Type(value = TvCrew.class, name = "tv")
})
public interface Crew {
    /**
     * Used to determine the type of Crew object without {@code instanceof()} or {@code getClass}.
     */
    MediaType getMediaType();
}
