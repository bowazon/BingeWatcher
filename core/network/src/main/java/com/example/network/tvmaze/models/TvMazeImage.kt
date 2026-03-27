package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeImage(
    val medium: String,
    val original: String,
)