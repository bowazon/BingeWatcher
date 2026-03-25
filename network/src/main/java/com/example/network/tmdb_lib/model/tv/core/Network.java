package com.example.network.tmdb_lib.model.tv.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.NamedIdElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Network extends NamedIdElement {
    @JsonProperty("logo_path")
    private String logoPath;

    @JsonProperty("origin_country")
    private String originCountry;
}
