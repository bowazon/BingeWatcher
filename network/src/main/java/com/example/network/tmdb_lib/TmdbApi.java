package com.example.network.tmdb_lib;

import com.example.network.tmdb_lib.TmdbAccount;
import com.example.network.tmdb_lib.TmdbAuthentication;
import com.example.network.tmdb_lib.TmdbCertifications;
import com.example.network.tmdb_lib.TmdbChanges;
import com.example.network.tmdb_lib.TmdbCollections;
import com.example.network.tmdb_lib.TmdbCompanies;
import com.example.network.tmdb_lib.TmdbConfiguration;
import com.example.network.tmdb_lib.TmdbDiscover;
import com.example.network.tmdb_lib.TmdbFind;
import com.example.network.tmdb_lib.TmdbGenre;
import com.example.network.tmdb_lib.TmdbGuestSessions;
import com.example.network.tmdb_lib.TmdbKeywords;
import com.example.network.tmdb_lib.TmdbLists;
import com.example.network.tmdb_lib.TmdbMovieLists;
import com.example.network.tmdb_lib.TmdbMovies;
import com.example.network.tmdb_lib.TmdbNetworks;
import com.example.network.tmdb_lib.TmdbPeople;
import com.example.network.tmdb_lib.TmdbPeopleLists;
import com.example.network.tmdb_lib.TmdbReviews;
import com.example.network.tmdb_lib.TmdbSearch;
import com.example.network.tmdb_lib.TmdbTrending;
import com.example.network.tmdb_lib.TmdbTvEpisodeGroups;
import com.example.network.tmdb_lib.TmdbTvEpisodes;
import com.example.network.tmdb_lib.TmdbTvSeasons;
import com.example.network.tmdb_lib.TmdbTvSeries;
import com.example.network.tmdb_lib.TmdbTvSeriesLists;
import com.example.network.tmdb_lib.TmdbWatchProviders;
import com.example.network.tmdb_lib.tools.TmdbHttpClient;
import com.example.network.tmdb_lib.tools.TmdbUrlReader;


import lombok.AccessLevel;
import lombok.Getter;

/**
 * The movie db api for getting started. See the
 * <a href="https://developer.themoviedb.org/docs/getting-started">documentation</a> for more info.
 *
 * @author Holger Brandl.
 */
public class TmdbApi {
    /**
     * Http client to make requests to the movie database api.
     */
    @Getter(AccessLevel.PROTECTED)
    private final TmdbUrlReader tmdbUrlReader;

    /**
     * Constructor.
     *
     * @param apiKey your TMDB api key
     */
    public TmdbApi(String apiKey) {
        this(new TmdbHttpClient(apiKey));
    }

    /**
     * Constructor.
     *
     * @param tmdbUrlReader the url reader to use
     */
    public TmdbApi(TmdbUrlReader tmdbUrlReader) {
        this.tmdbUrlReader = tmdbUrlReader;
    }

    public com.example.network.tmdb_lib.TmdbAccount getAccount() {
        return new TmdbAccount(this);
    }

    public TmdbAuthentication getAuthentication() {
        return new TmdbAuthentication(this);
    }

    public com.example.network.tmdb_lib.TmdbCertifications getCertifications() {
        return new TmdbCertifications(this);
    }

    public com.example.network.tmdb_lib.TmdbChanges getChanges() {
        return new TmdbChanges(this);
    }

    public com.example.network.tmdb_lib.TmdbCollections getCollections() {
        return new TmdbCollections(this);
    }

    public com.example.network.tmdb_lib.TmdbCompanies getCompanies() {
        return new TmdbCompanies(this);
    }

    public com.example.network.tmdb_lib.TmdbConfiguration getConfiguration() {
        return new TmdbConfiguration(this);
    }

    public com.example.network.tmdb_lib.TmdbDiscover getDiscover() {
        return new TmdbDiscover(this);
    }

    public com.example.network.tmdb_lib.TmdbFind getFind() {
        return new TmdbFind(this);
    }

    public com.example.network.tmdb_lib.TmdbGenre getGenre() {
        return new TmdbGenre(this);
    }

    public com.example.network.tmdb_lib.TmdbGuestSessions getGuestSessions() {
        return new TmdbGuestSessions(this);
    }

    public com.example.network.tmdb_lib.TmdbKeywords getKeywords() {
        return new TmdbKeywords(this);
    }

    public com.example.network.tmdb_lib.TmdbLists getLists() {
        return new TmdbLists(this);
    }

    public com.example.network.tmdb_lib.TmdbMovieLists getMovieLists() {
        return new TmdbMovieLists(this);
    }

    public com.example.network.tmdb_lib.TmdbMovies getMovies() {
        return new TmdbMovies(this);
    }

    public com.example.network.tmdb_lib.TmdbNetworks getNetworks() {
        return new TmdbNetworks(this);
    }

    public com.example.network.tmdb_lib.TmdbPeopleLists getPeopleLists() {
        return new TmdbPeopleLists(this);
    }

    public com.example.network.tmdb_lib.TmdbPeople getPeople() {
        return new TmdbPeople(this);
    }

    public com.example.network.tmdb_lib.TmdbReviews getReviews() {
        return new TmdbReviews(this);
    }

    public com.example.network.tmdb_lib.TmdbSearch getSearch() {
        return new TmdbSearch(this);
    }

    public com.example.network.tmdb_lib.TmdbTrending getTrending() {
        return new TmdbTrending(this);
    }

    public com.example.network.tmdb_lib.TmdbTvEpisodes getTvEpisodes() {
        return new TmdbTvEpisodes(this);
    }

    public com.example.network.tmdb_lib.TmdbTvEpisodeGroups getTvEpisodeGroups() {
        return new TmdbTvEpisodeGroups(this);
    }

    public com.example.network.tmdb_lib.TmdbTvSeasons getTvSeasons() {
        return new TmdbTvSeasons(this);
    }

    public com.example.network.tmdb_lib.TmdbTvSeries getTvSeries() {
        return new TmdbTvSeries(this);
    }

    public com.example.network.tmdb_lib.TmdbTvSeriesLists getTvSeriesLists() {
        return new TmdbTvSeriesLists(this);
    }

    public com.example.network.tmdb_lib.TmdbWatchProviders getWatchProviders() {
        return new TmdbWatchProviders(this);
    }
}
