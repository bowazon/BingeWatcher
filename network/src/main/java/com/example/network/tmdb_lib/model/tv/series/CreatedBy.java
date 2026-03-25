package com.example.network.tmdb_lib.model.tv.series;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.NamedIdElement;
import com.example.network.tmdb_lib.model.people.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreatedBy extends NamedIdElement {
    @JsonProperty("credit_id")
    private String creditId;

    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("profile_path")
    private String profilePath;
}
