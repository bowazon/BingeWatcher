package com.example.network.tmdb_lib.model.people;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    NOT_SPECIFIED,
    FEMALE,
    MALE,
    NON_BINARY;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}
