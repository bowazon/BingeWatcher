package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeShowImage(
    val id: Long,
    val type: String,
    val main: Boolean,
    val resolutions: TvMazeResolutions,
)