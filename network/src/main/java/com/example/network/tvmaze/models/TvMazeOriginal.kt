package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeOriginal(
    val url: String,
    val width: Long,
    val height: Long,
)