package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazePerson (
    val score: Double,
    val person: TvMazePersonDetails,
)