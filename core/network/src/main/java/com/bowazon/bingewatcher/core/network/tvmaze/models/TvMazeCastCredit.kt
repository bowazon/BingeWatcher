package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvMazeCastCredit(
    val self: Boolean,
    val voice: Boolean,
    //@JsonProperty("_links")
    @SerialName("_links") val links: TvMazeActorAssociatedWith,
    @SerialName("_embedded") val embedded: TvMazeEmbedded? = null,
)