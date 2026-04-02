package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeCrewCredit(
    val type: String,
    //@JsonProperty("_links")
    //val links: CrewCreditLinks,
)