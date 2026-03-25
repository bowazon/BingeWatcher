package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeCrewCredit(
    val type: String,
    //@JsonProperty("_links")
    //val links: CrewCreditLinks,
)