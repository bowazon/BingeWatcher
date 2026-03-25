package com.example.network.tmdb_lib.model.tv.season;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.IdElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ExternalIds extends IdElement {
    @JsonProperty("freebase_id")
    private String freebaseId;

    @JsonProperty("freebase_mid")
    private String freebaseMid;

    @JsonProperty("tvdb_id")
    private String tvdbId;

    @JsonProperty("tvrage_id")
    private String tvrageId;

    @JsonProperty("wikidata_id")
    private String wikidataId;
}
