package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeSeason(
    val id: Long,
    val url: String,
    val number: Long,
    val name: String,
    val episodeOrder: Long? = null,
    val premiereDate: String,
    val endDate: String? = null,
    val network: TvMazeNetwork? = null,
    val webChannel: TvMazeWebChannel? = null,
    val image: TvMazeImage? = null,
    val summary: String? = null,
    //@JsonProperty("_links")
    //val links: Links,
)