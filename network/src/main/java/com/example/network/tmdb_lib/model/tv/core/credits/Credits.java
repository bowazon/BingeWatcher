package com.example.network.tmdb_lib.model.tv.core.credits;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.IdElement;
import com.example.network.tmdb_lib.model.tv.core.credits.Cast;
import com.example.network.tmdb_lib.model.tv.core.credits.Crew;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Credits extends IdElement {
    @JsonProperty("cast")
    private List<Cast> cast = new ArrayList<>();

    @JsonProperty("crew")
    private List<Crew> crew = new ArrayList<>();
}
