package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazePreviousEpisode(
    val href: String,
    val name: String,
)