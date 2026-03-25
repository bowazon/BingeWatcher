package com.example.network.tmdb_lib.model.tv.episodegroups;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.NamedStringIdElement;
import com.example.network.tmdb_lib.model.tv.core.Network;
import com.example.network.tmdb_lib.model.tv.episodegroups.EpisodeGroupType;
import com.example.network.tmdb_lib.model.tv.episodegroups.TvEpisodeGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TvEpisodeGroups extends NamedStringIdElement {
    @JsonProperty("description")
    private String description;

    @JsonProperty("episode_count")
    private Integer episodeCount;

    @JsonProperty("group_count")
    private Integer groupCount;

    @JsonProperty("groups")
    private List<TvEpisodeGroup> groups = new ArrayList<>();

    @JsonProperty("network")
    private Network network;

    @JsonProperty("type")
    private EpisodeGroupType type;
}
