package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazePersonDetails(
    val id: Long,
    val url: String,
    val name: String,
    val country: TvMazeCountry?,
    val birthday: String?,
    val deathday: String?,
    val gender: String,
    val image: TvMazeImage?,
    val updated: Long,
    //@JsonProperty("_links")
    //val links: Links,
)