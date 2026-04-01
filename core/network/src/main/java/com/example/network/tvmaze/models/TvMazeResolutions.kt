package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeResolutions(
    val original: TvMazeOriginal? = null,
    val medium: TvMazeMedium? = null,
)