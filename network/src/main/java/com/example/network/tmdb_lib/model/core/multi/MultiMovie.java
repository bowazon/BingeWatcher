package com.example.network.tmdb_lib.model.core.multi;

import com.example.network.tmdb_lib.model.core.Movie;
import com.example.network.tmdb_lib.model.core.multi.Multi;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MultiMovie extends Movie implements Multi {
    @Override
    public MediaType getMediaType() {
        return MediaType.MOVIE;
    }
}
