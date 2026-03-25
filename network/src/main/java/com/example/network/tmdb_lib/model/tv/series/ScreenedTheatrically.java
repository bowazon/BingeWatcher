package com.example.network.tmdb_lib.model.tv.series;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.IdElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScreenedTheatrically extends IdElement {
    @JsonProperty("episode_number")
    private Integer episodeNumber;

    @JsonProperty("season_number")
    private Integer seasonNumber;
}
