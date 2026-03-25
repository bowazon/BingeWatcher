package com.example.network.tmdb_lib.model.find;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.AbstractJsonMapping;
import com.example.network.tmdb_lib.model.find.FindMovie;
import com.example.network.tmdb_lib.model.find.FindPerson;
import com.example.network.tmdb_lib.model.find.FindTvEpisode;
import com.example.network.tmdb_lib.model.find.FindTvSeason;
import com.example.network.tmdb_lib.model.find.FindTvSeries;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FindResults extends AbstractJsonMapping {
    @JsonProperty("movie_results")
    private List<FindMovie> movieResults = new ArrayList<>();

    @JsonProperty("person_results")
    private List<FindPerson> personResults = new ArrayList<>();

    @JsonProperty("tv_results")
    private List<FindTvSeries> tvSeriesResults = new ArrayList<>();

    @JsonProperty("tv_season_results")
    private List<FindTvSeason> tvSeasonResults = new ArrayList<>();

    @JsonProperty("tv_episode_results")
    private List<FindTvEpisode> tvEpisodeResults = new ArrayList<>();
}
