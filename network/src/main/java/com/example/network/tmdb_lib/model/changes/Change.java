package com.example.network.tmdb_lib.model.changes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.IdElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Change extends IdElement {
    @JsonProperty("adult")
    private Boolean adult;
}
