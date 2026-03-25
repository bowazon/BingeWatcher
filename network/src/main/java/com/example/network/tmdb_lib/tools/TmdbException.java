package com.example.network.tmdb_lib.tools;

/**
 * TMDb-API related exceptions.
 */
public class TmdbException extends Exception {
    public TmdbException(String message) {
        super(message);
    }

    public TmdbException(Exception exception) {
        super(exception);
    }
}
