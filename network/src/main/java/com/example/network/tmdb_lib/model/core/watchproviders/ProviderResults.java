package com.example.network.tmdb_lib.model.core.watchproviders;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.IdElement;
import com.example.network.tmdb_lib.model.core.watchproviders.WatchProviders;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProviderResults extends IdElement {
    @JsonProperty("results")
    private Map<String, WatchProviders> results = new HashMap<>();
}
