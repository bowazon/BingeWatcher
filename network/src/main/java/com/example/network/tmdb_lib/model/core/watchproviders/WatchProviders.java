package com.example.network.tmdb_lib.model.core.watchproviders;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.AbstractJsonMapping;
import com.example.network.tmdb_lib.model.core.watchproviders.Provider;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WatchProviders extends AbstractJsonMapping {
    @JsonProperty("link")
    private String link;

    @JsonProperty("rent")
    private List<com.example.network.tmdb_lib.model.core.watchproviders.Provider> rentProviders = new ArrayList<>();

    @JsonProperty("buy")
    private List<com.example.network.tmdb_lib.model.core.watchproviders.Provider> buyProviders = new ArrayList<>();

    @JsonProperty("flatrate")
    private List<com.example.network.tmdb_lib.model.core.watchproviders.Provider> flatrateProviders = new ArrayList<>();

    @JsonProperty("free")
    private List<com.example.network.tmdb_lib.model.core.watchproviders.Provider> freeProviders = new ArrayList<>();

    @JsonProperty("ads")
    private List<Provider> adsProviders = new ArrayList<>();
}
