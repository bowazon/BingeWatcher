package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeWebChannel(
    val id: Long? = null,
    val name: String? = null,
    val country: TvMazeCountry? = null,
    val officialSite: String? = null,
)