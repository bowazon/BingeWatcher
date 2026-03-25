package com.example.network.tmdb_lib.model.tv.episodegroups;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.tv.core.TvEpisode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderedTvEpisode extends TvEpisode {
    @JsonProperty("order")
    private Integer order;
}
