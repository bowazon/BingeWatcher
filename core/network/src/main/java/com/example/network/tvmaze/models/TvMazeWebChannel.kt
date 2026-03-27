package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeWebChannel(
    val id: Long?,
    val name: String?,
    val country: TvMazeCountry?,
    val officialSite: String?,
)