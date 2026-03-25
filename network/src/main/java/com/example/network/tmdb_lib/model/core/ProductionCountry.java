package com.example.network.tmdb_lib.model.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import com.example.network.tmdb_lib.model.core.AbstractJsonMapping;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonRootName("production_country")
public class ProductionCountry extends AbstractJsonMapping {
    @JsonProperty("iso_3166_1")
    private String isoCode;

    @JsonProperty("name")
    private String name;
}
