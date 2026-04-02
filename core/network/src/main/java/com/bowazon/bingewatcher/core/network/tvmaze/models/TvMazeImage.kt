package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeImage(
    val medium: String? = null,
    val original: String? = null,
)