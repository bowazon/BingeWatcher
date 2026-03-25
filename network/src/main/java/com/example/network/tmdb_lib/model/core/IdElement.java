package com.example.network.tmdb_lib.model.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.example.network.tmdb_lib.model.core.AbstractJsonMapping;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Base class for json wrappers with id element.
 *
 * @author Holger Brandl
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class IdElement extends AbstractJsonMapping {
    @JsonProperty("id")
    private int id;
}
