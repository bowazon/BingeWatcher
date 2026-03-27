package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeResolutions(
    val original: TvMazeOriginal,
    val medium: TvMazeMedium?,
)