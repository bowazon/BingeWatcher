package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeCrewMember(
    val type: String,
    val person: TvMazePerson,
)