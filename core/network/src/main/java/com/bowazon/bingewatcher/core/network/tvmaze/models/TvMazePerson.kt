package com.bowazon.bingewatcher.core.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazePerson(
    val id: Long,
    val url: String? = null,
    val name: String,
    val country: TvMazeCountry? = null,
    val birthday: String? = null,
    val deathday: String? = null,
    val gender: String? = null,
    val image: TvMazeImage? = null,
    val updated: Long? = null,
    //@JsonProperty("_links")
    //val links: Links,
)