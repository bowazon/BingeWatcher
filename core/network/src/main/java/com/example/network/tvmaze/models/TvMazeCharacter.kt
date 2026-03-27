package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeCharacter(
    val id: Long,
    val url: String,
    val name: String,
    val image: TvMazeImage?,
    //@JsonProperty("_links")
    //val links: ,
)