package com.example.network.tmdb_lib.model.core.responses;

import com.example.network.tmdb_lib.tools.TmdbException;
import com.example.network.tmdb_lib.tools.TmdbResponseCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TmdbResponseException extends TmdbException {
    private final TmdbResponseCode responseCode;

    //public TmdbResponseCode getTmdbResponseCode() {
    //    return responseCode;
    //}

    public TmdbResponseException(TmdbResponseCode responseCode) {
        super(responseCode.toString());
        this.responseCode = responseCode;
    }

    public TmdbResponseException(String message) {
        super(message);
        this.responseCode = null;
    }

    public TmdbResponseException(Exception exception) {
        super(exception);
        this.responseCode = null;
    }
}
