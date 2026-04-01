package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeSeason(
    val id: Long,
    val url: String,
    val number: Long,
    val name: String,
    val episodeOrder: Long,
    val premiereDate: String,
    val endDate: String,
    val network: TvMazeNetwork,
    val webChannel: String? = null,
    val image: TvMazeImage? = null,
    val summary: String? = null,
    //@JsonProperty("_links")
    //val links: Links,
)