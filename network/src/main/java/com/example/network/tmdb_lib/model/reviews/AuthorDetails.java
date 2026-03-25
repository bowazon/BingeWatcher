package com.example.network.tmdb_lib.model.reviews;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.NamedElement;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthorDetails extends NamedElement {
    @JsonProperty("username")
    private String username;

    @JsonProperty("avatar_path")
    private String avatarPath;

    @JsonProperty("rating")
    private String rating;
}
