package com.example.network.tmdb_lib.model.movies;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.IdElement;
import com.example.network.tmdb_lib.model.keywords.Keyword;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class KeywordResults extends IdElement {
    @JsonProperty("keywords")
    private List<Keyword> keywords = new ArrayList<>();
}
