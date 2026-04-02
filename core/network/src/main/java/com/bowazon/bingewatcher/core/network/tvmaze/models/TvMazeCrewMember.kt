package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeCrewMember(
    val type: String,
    val person: TvMazePerson,
)