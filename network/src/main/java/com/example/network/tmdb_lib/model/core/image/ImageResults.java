package com.example.network.tmdb_lib.model.core.image;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.IdElement;
import com.example.network.tmdb_lib.model.core.image.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ImageResults extends IdElement {
    @JsonProperty("logos")
    private List<Image> logos = new ArrayList<>();
}
