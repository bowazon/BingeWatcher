package com.example.network.tmdb_lib.model.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.NamedIdElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Company extends NamedIdElement {
    @JsonProperty("logo_path")
    private String logoPath;

    @JsonProperty("origin_country")
    private String originCountry;
}
