package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeExternals(
    val tvrage: Long? = null,
    val thetvdb: Long? = null,
    val imdb: String? = null,
)