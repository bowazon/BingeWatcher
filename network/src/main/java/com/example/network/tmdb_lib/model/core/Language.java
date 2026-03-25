package com.example.network.tmdb_lib.model.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.example.network.tmdb_lib.model.core.AbstractJsonMapping;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Language extends AbstractJsonMapping {
    @JsonProperty("iso_639_1")
    private String iso6391;

    @JsonProperty("english_name")
    private String englishName;

    @JsonProperty("name")
    private String name;
}
