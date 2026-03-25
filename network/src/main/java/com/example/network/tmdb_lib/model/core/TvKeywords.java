package com.example.network.tmdb_lib.model.core;

import com.example.network.tmdb_lib.model.core.Results;
import com.example.network.tmdb_lib.model.keywords.Keyword;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TvKeywords extends Results<Keyword> {
}
