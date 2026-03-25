package com.example.network.tmdb_lib.model.tv.core;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.AbstractJsonMapping;
import com.example.network.tmdb_lib.model.tv.core.ChangeItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Change extends AbstractJsonMapping {
    @JsonProperty("key")
    private String key;

    @JsonProperty("items")
    private List<ChangeItem> changeItems = new ArrayList<>();
}
