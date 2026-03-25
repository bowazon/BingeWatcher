package com.example.network.tmdb_lib.tools;

import com.example.network.tmdb_lib.model.core.responses.TmdbResponseException;
import com.example.network.tmdb_lib.tools.RequestType;

/**
 * Interface for reading URLs from TMDB API.
 */
public interface TmdbUrlReader {
    /**
     * Reads a url and returns the response.
     *
     * @param url         the url to make the request to
     * @param jsonBody    the json body to send with the request
     * @param requestType the type of request to make
     * @return the response from the movie database api
     * @throws TmdbResponseException if the response was not successful
     */
    String readUrl(String url, String jsonBody, RequestType requestType) throws TmdbResponseException;
}
