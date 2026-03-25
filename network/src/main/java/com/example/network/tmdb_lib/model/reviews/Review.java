package com.example.network.tmdb_lib.model.reviews;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.StringIdElement;
import com.example.network.tmdb_lib.model.reviews.AuthorDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Review extends StringIdElement {
    @JsonProperty("author")
    private String author;

    @JsonProperty("author_details")
    private AuthorDetails authorDetails;

    @JsonProperty("content")
    private String content;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("iso_639_1")
    private String iso6391;

    @JsonProperty("media_id")
    private Integer mediaId;

    @JsonProperty("media_title")
    private String mediaTitle;

    @JsonProperty("media_type")
    private String mediaType;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("url")
    private String url;
}
