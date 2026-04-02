package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeCountry(
    val name: String? = null,
    val code: String? = null,
    val timezone: String? = null,
)