package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeEpisode(
    val id: Long,
    val url: String,
    val name: String,
    val season: Long,
    val number: Long,
    val type: String? = null,
    val airdate: String? = null,
    val airtime: String? = null,
    val airstamp: String? = null,
    val runtime: Long? = null,
    val rating: TvMazeRating? = null,
    val image: TvMazeImage? = null,
    val summary: String? = null,
    //@JsonProperty("_links")
    val links: TvMazeLinks? = null,
    //@JsonProperty("_embedded")
    val embedded: TvMazeEmbedded? = null,
)