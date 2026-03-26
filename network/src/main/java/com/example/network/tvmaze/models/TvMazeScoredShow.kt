package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeScoredShow(
    val score: Double,
    val show: TvMazeShow
)