package com.example.network.tmdb_lib.model.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.AbstractJsonMapping;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Session extends AbstractJsonMapping {
    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty("success")
    private Boolean success;
}
