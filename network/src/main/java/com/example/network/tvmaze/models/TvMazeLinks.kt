package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeLinks(
    val self: TvMazeSelf,
    val previousEpisode: TvMazePreviousEpisode,
)