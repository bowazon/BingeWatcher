package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeActor(
    val person: TvMazePerson,
    val character: TvMazeCharacter,
    val self: Boolean,
    val voice: Boolean,
)