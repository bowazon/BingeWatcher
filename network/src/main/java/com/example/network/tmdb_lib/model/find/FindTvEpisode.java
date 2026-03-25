package com.example.network.tmdb_lib.model.find;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.tv.core.TvEpisode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FindTvEpisode extends TvEpisode {
    @JsonProperty("episode_type")
    private String episodeType;
}
