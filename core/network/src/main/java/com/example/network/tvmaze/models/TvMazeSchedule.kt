package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeSchedule(
    val time: String,
    val days: List<String>,
)