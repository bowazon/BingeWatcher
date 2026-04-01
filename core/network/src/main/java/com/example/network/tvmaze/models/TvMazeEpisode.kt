package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeEpisode(
    val id: Long,
    val url: String,
    val name: String,
    val season: Long,
    val number: Long?,
    val type: String,
    val airdate: String,
    val airtime: String,
    val airstamp: String,
    val runtime: Long? = null,
    val rating: TvMazeRating,
    val image: TvMazeImage? = null,
    val summary: String? = null,
    //@JsonProperty("_links")
    val links: TvMazeLinks,
    //@JsonProperty("_embedded")
    val embedded: TvMazeEmbedded? = null,
)