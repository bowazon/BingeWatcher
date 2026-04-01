package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeCrewAssociatedWith(
    val show: TvMazeShowLink,
)