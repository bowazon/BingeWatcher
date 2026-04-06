package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvMazeCrewCredit(
    val type: String,
    @SerialName("_embedded") val embedded: TvMazeEmbedded? = null,
    //@JsonProperty("_links")
    //val links: CrewCreditLinks,
)