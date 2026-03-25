package com.example.network.tmdb_lib.model.find;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.tv.core.TvSeason;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FindTvSeason extends TvSeason {
    @JsonProperty("show_id")
    private Integer showId;
}
