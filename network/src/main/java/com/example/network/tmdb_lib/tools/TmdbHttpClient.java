package com.example.network.tmdb_lib.tools;

import java.io.IOException;

import com.example.network.tmdb_lib.model.core.responses.TmdbResponseException;
import com.example.network.tmdb_lib.tools.RequestType;
import com.example.network.tmdb_lib.tools.TmdbUrlReader;
import lombok.AllArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to make requests to the movie database api.
 */
@AllArgsConstructor
public class TmdbHttpClient implements TmdbUrlReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(TmdbHttpClient.class);

    OkHttpClient client;// = new OkHttpClient();

    private final String apiKey;

    public TmdbHttpClient(String apiKey) {
        this.apiKey = apiKey;

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // BODY, HEADERS, BASIC, NONE

        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Override
    public String readUrl(String urlStr, String jsonBody, RequestType requestType) throws TmdbResponseException {
        LOGGER.debug("TMDB API: making request, of type: {}, to: {}", requestType.toString(), urlStr);

        Request.Builder request = new Request.Builder()
                .header("Authorization", "Bearer " + apiKey)
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .url(urlStr);

        //.header("Authorization", "Bearer " + apiKey)
        //            .header("Accept", "application/json")
        //            .header("Content-type", "application/json");

        switch (requestType) {
            case GET -> request.get();
            case POST -> {
                MediaType JSON = MediaType.get("application/json; charset=utf-8");
                request.post(RequestBody.create(jsonBody, JSON));
            }
            case DELETE -> request.delete();
            default -> throw new RuntimeException("Invalid request type: " + requestType);
        }

        // test
        request = new Request.Builder()
                .header("Authorization", "Bearer " + apiKey)
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .url("https://api.themoviedb.org/3/movie/latest");

        try {
            Response response = client.newCall(request.build()).execute();
            //HttpResponse<String> httpResponse = httpClient.send(request.build(), HttpResponse.BodyHandlers.ofString());
            //if (responseBody == null) {
                //throw new TmdbResponseException("Response body was null: " + httpResponse);
            //}
            return response.body().string();
        }
        catch (IOException exception) {
            throw new TmdbResponseException(exception);
        }
    }
}
