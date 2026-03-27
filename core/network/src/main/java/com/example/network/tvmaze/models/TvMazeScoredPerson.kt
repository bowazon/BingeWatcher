package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeScoredPerson(
    val score: Double,
    val person: TvMazePerson
)