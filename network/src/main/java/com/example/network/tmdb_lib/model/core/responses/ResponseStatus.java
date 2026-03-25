package com.example.network.tmdb_lib.model.core.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.AbstractJsonMapping;
import com.example.network.tmdb_lib.tools.TmdbResponseCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseStatus extends AbstractJsonMapping {
    @JsonProperty("status_code")
    private TmdbResponseCode statusCode;

    @JsonProperty("status_message")
    private String statusMessage;
}
