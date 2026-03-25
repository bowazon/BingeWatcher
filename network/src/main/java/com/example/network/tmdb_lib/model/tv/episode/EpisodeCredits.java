package com.example.network.tmdb_lib.model.tv.episode;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.network.tmdb_lib.model.tv.core.credits.Credits;
import com.example.network.tmdb_lib.model.tv.episode.GuestStar;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EpisodeCredits extends Credits {
    @JsonProperty("guest_stars")
    private List<GuestStar> guestStars = new ArrayList<>();
}
