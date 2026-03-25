package com.example.network.tmdb_lib.model.tv.season;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.core.NamedIdElement;
import com.example.network.tmdb_lib.model.core.video.VideoResults;
import com.example.network.tmdb_lib.model.core.watchproviders.ProviderResults;
import com.example.network.tmdb_lib.model.tv.core.Translations;
import com.example.network.tmdb_lib.model.tv.core.credits.AggregateCredits;
import com.example.network.tmdb_lib.model.tv.core.credits.Credits;
import com.example.network.tmdb_lib.model.tv.season.AccountStateResults;
import com.example.network.tmdb_lib.model.tv.season.ExternalIds;
import com.example.network.tmdb_lib.model.tv.season.Images;
import com.example.network.tmdb_lib.model.tv.season.TvSeasonEpisode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TvSeasonDb extends NamedIdElement {
    @JsonProperty("_id")
    private String underscoreId;

    @JsonProperty("air_date")
    private String airDate;

    @JsonProperty("episodes")
    private List<TvSeasonEpisode> episodes = new ArrayList<>();

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("season_number")
    private Integer seasonNumber;

    @JsonProperty("vote_average")
    private Double voteAverage;

    /* append to responses */

    /** Can be null if not appended to the request (append to response). */
    @JsonProperty("account_states")
    private AccountStateResults accountStates;

    /** Can be null if not appended to the request (append to response). */
    @JsonProperty("aggregate_credits")
    private AggregateCredits aggregateCredits;

    /** Can be null if not appended to the request (append to response). */
    @JsonProperty("credits")
    private Credits credits;

    /** Can be null if not appended to the request (append to response). */
    @JsonProperty("external_ids")
    private ExternalIds externalIds;

    /** Can be null if not appended to the request (append to response). */
    @JsonProperty("images")
    private Images images;

    /** Can be null if not appended to the request (append to response). */
    @JsonProperty("translations")
    private Translations translations;

    /** Can be null if not appended to the request (append to response). */
    @JsonProperty("videos")
    private VideoResults videos;

    /** Can be null if not appended to the request (append to response). */
    @JsonProperty("watch/providers")
    private ProviderResults watchProviders;
}
