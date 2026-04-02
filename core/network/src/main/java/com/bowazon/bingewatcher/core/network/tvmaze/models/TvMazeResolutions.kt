package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeResolutions(
    val original: TvMazeOriginal? = null,
    val medium: TvMazeMedium? = null,
)