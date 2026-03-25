package com.example.network.tmdb_lib.model.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.example.network.tmdb_lib.model.core.IdElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NamedIdElement extends IdElement {
    @JsonProperty("name")
    private String name;
}
