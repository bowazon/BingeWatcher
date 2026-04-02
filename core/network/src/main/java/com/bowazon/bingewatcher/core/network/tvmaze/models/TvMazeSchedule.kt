package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeSchedule(
    val time: String?,
    val days: List<String>? = null,
)