package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeShowImage(
    val id: Long,
    val type: String? = null,
    val main: Boolean,
    val resolutions: TvMazeResolutions? = null,
)