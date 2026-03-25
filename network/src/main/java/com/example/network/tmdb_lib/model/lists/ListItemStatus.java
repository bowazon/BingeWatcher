package com.example.network.tmdb_lib.model.lists;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.IdElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Holger Brandl
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ListItemStatus extends IdElement {
    @JsonProperty("item_present")
    private Boolean itemPresent;
}
