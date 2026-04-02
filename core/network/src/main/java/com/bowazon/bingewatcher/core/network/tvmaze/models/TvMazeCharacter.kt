package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeCharacter(
    val id: Long,
    val url: String,
    val name: String,
    val image: TvMazeImage? = null,
    //@JsonProperty("_links")
    //val links: ,
)