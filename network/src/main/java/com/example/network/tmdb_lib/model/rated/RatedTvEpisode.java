package com.example.network.tmdb_lib.model.rated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.NamedIdElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RatedTvEpisode extends NamedIdElement {
    @JsonProperty("air_date")
    private String airDate;

    @JsonProperty("episode_number")
    private Integer episodeNumber;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("production_code")
    private String productionCode;

    @JsonProperty("runtime")
    private Integer runtime;

    @JsonProperty("season_number")
    private Integer seasonNumber;

    @JsonProperty("show_id")
    private Integer showId;

    @JsonProperty("still_path")
    private String stillPath;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("vote_count")
    private Integer voteCount;

    @JsonProperty("rating")
    private Double rating;
}
