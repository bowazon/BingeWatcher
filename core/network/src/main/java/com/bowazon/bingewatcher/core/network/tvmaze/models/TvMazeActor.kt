package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeActor(
    val person: TvMazePerson? = null,
    val character: TvMazeCharacter? = null,
    val self: Boolean? = null,
    val voice: Boolean? = null,
)