package com.example.network.tmdb_lib.model.core.multi;

import com.example.network.tmdb_lib.model.core.multi.Multi;
import com.example.network.tmdb_lib.model.core.popularperson.PopularPerson;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MultiPerson extends PopularPerson implements Multi {
    @Override
    public MediaType getMediaType() {
        return MediaType.PERSON;
    }
}
