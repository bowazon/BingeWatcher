package com.example.network.tmdb_lib.model.core.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.AbstractJsonMapping;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ResponseStatusDelete extends AbstractJsonMapping {
    @JsonProperty("success")
    private Boolean success;
}
