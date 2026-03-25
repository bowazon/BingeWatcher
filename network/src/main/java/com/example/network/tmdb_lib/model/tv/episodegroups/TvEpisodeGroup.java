package com.example.network.tmdb_lib.model.tv.episodegroups;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.NamedStringIdElement;
import com.example.network.tmdb_lib.model.tv.episodegroups.OrderedTvEpisode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TvEpisodeGroup extends NamedStringIdElement {
    @JsonProperty("order")
    private Integer order;

    @JsonProperty("episodes")
    private List<OrderedTvEpisode> episodes = new ArrayList<>();

    @JsonProperty("locked")
    private Boolean locked;
}
