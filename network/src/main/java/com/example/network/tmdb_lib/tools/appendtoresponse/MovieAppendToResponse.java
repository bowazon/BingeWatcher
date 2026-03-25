package com.example.network.tmdb_lib.tools.appendtoresponse;

import com.example.network.tmdb_lib.tools.appendtoresponse.AppendToResponse;

/**
 * Movie appendable responses.
 */
public enum MovieAppendToResponse implements AppendToResponse {
    ACCOUNT_STATES("account_states"),
    ALTERNATIVE_TITLES("alternative_titles"),
    CREDITS("credits"),
    CHANGES("changes"),
    EXTERNAL_IDS("external_ids"),
    IMAGES("images"),
    KEYWORDS("keywords"),
    LISTS("lists"),
    RECOMMENDATIONS("recommendations"),
    RELEASE_DATES("release_dates"),
    REVIEWS("reviews"),
    SIMILAR("similar"),
    TRANSLATIONS("translations"),
    VIDEOS("videos"),
    WATCH_PROVIDERS("watch/providers");

    private final String value;

    MovieAppendToResponse(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
