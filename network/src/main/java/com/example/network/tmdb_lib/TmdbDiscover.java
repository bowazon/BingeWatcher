package com.example.network.tmdb_lib;

import com.example.network.tmdb_lib.AbstractTmdbApi;
import com.example.network.tmdb_lib.TmdbApi;
import com.example.network.tmdb_lib.model.core.MovieResultsPage;
import com.example.network.tmdb_lib.model.core.TvSeriesResultsPage;
import com.example.network.tmdb_lib.tools.ApiUrl;
import com.example.network.tmdb_lib.tools.TmdbException;
import com.example.network.tmdb_lib.tools.builders.discover.DiscoverMovieParamBuilder;
import com.example.network.tmdb_lib.tools.builders.discover.DiscoverTvParamBuilder;

/**
 * The movie database api for discover. See the
 * <a href="https://developer.themoviedb.org/reference/discover-movie">documentation</a> for more info.
 */
public class TmdbDiscover extends AbstractTmdbApi {
    protected static final String TMDB_METHOD_DISCOVER = "discover";
    protected static final String TMDB_METHOD_MOVIE = "movie";
    protected static final String TMDB_METHOD_TV = "tv";

    /**
     * Create a new TmdbDiscover instance to call the discover related TMDb API methods.
     */
    TmdbDiscover(TmdbApi tmdbApi) {
        super(tmdbApi);
    }

    /**
     * <p>Find movies using over 30 filters and sort options.</p>
     * <p>See the <a href="https://developer.themoviedb.org/reference/discover-movie">documentation</a> for more info.</p>
     *
     * @param builder A discover object containing the search criteria wanted
     * @return the movie results page.
     */
    public MovieResultsPage getMovie(DiscoverMovieParamBuilder builder) throws TmdbException {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_DISCOVER, TMDB_METHOD_MOVIE)
            .addPathParams(builder);
        return mapJsonResult(apiUrl, MovieResultsPage.class);
    }

    /**
     * <p>Find TV shows using over 30 filters and sort options.</p>
     * <p>See the <a href="https://developer.themoviedb.org/reference/discover-tv">documentation</a> for more info.</p>
     *
     * @param builder A discover object containing the search criteria wanted
     * @return the tv series results page.
     */
    public TvSeriesResultsPage getTv(DiscoverTvParamBuilder builder) throws TmdbException {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_DISCOVER, TMDB_METHOD_TV)
            .addPathParams(builder);
        return mapJsonResult(apiUrl, TvSeriesResultsPage.class);
    }
}
