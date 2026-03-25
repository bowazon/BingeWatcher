package com.example.network.tmdb_lib.model.lists;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.responses.ResponseStatusAuthentication;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovieListCreationStatus extends ResponseStatusAuthentication {
    @JsonProperty("list_id")
    private String listId;
}
