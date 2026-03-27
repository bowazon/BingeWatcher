package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeExternals(
    val tvrage: Long?,
    val thetvdb: Long?,
    val imdb: String?,
)