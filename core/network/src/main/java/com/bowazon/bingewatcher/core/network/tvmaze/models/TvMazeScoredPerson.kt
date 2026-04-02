package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeScoredPerson(
    val score: Double,
    val person: TvMazePerson
)