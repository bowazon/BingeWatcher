package com.example.network.tmdb_lib.model.core.multi;

import com.example.network.tmdb_lib.model.core.TvSeries;
import com.example.network.tmdb_lib.model.core.multi.Multi;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MultiTvSeries extends TvSeries implements Multi {
    @Override
    public MediaType getMediaType() {
        return MediaType.TV_SERIES;
    }
}
