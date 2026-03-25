package com.example.network.tmdb_lib;

import com.example.network.tmdb_lib.AbstractTmdbApi;
import com.example.network.tmdb_lib.TmdbApi;
import com.example.network.tmdb_lib.TmdbDiscover;
import com.example.network.tmdb_lib.model.core.MovieDbResultsPage;
import com.example.network.tmdb_lib.model.keywords.Keyword;
import com.example.network.tmdb_lib.tools.ApiUrl;
import com.example.network.tmdb_lib.tools.TmdbException;
import com.example.network.tmdb_lib.tools.builders.discover.DiscoverMovieParamBuilder;

/**
 * The movie database api for keywords. See the
 * <a href="https://developer.themoviedb.org/reference/keyword-details">documentation</a> for more info.
 */
public class TmdbKeywords extends AbstractTmdbApi {
    protected static final String TMDB_METHOD_KEYWORD = "keyword";

    /**
     * Create a new TmdbKeywords instance to call the keywords TMDb API methods.
     */
    TmdbKeywords(TmdbApi tmdbApi) {
        super(tmdbApi);
    }

    /**
     * <p>Get the details for a specific keyword.</p>
     * <p>See the <a href="https://developer.themoviedb.org/reference/keyword-details">documentation</a> for more info.</p>
     *
     * @param keywordId The keyword id.
     * @return The keyword details.
     */
    public Keyword getDetails(int keywordId) throws TmdbException {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_KEYWORD, keywordId);
        return mapJsonResult(apiUrl, Keyword.class);
    }

    /**
     * Get the list of movies for a particular keyword by id.
     *
     * @return List of movies with the keyword
     * @deprecated use {@link TmdbDiscover#getMovie(DiscoverMovieParamBuilder)} instead.
     */
    @Deprecated
    public MovieDbResultsPage getKeywordMovies(String keywordId, String language, Integer page) throws TmdbException {
        ApiUrl apiUrl = new ApiUrl(TMDB_METHOD_KEYWORD, keywordId, "movies")
            .addLanguage(language)
            .addPage(page);

        return mapJsonResult(apiUrl, MovieDbResultsPage.class);
    }
}
