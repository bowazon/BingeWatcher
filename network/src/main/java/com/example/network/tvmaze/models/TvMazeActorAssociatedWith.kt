package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeActorAssociatedWith(
    val show: TvMazeShow,
    val character: TvMazeCharacterLink,
)