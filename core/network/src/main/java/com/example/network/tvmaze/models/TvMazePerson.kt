package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazePerson(
    val id: Long,
    val url: String,
    val name: String,
    val country: TvMazeCountry? = null,
    val birthday: String? = null,
    val deathday: String? = null,
    val gender: String,
    val image: TvMazeImage? = null,
    val updated: Long,
    //@JsonProperty("_links")
    //val links: Links,
)