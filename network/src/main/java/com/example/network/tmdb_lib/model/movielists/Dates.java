package com.example.network.tmdb_lib.model.movielists;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.AbstractJsonMapping;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Dates extends AbstractJsonMapping {
    @JsonProperty("maximum")
    private String maximum;

    @JsonProperty("minimum")
    private String minimum;
}
