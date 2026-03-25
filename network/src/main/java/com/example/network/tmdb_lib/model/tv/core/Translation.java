package com.example.network.tmdb_lib.model.tv.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.AbstractJsonMapping;
import com.example.network.tmdb_lib.model.tv.core.Data;
import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
public class Translation extends AbstractJsonMapping {
    @JsonProperty("iso_3166_1")
    private String iso31661;

    @JsonProperty("iso_639_1")
    private String iso6391;

    @JsonProperty("name")
    private String name;

    @JsonProperty("english_name")
    private String englishName;

    @JsonProperty("data")
    private Data data;
}
