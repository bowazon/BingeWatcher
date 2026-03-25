package com.example.network.tmdb_lib.model.rated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.TvSeries;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RatedTvSeries extends TvSeries {
    @JsonProperty("rating")
    private Double rating;
}
