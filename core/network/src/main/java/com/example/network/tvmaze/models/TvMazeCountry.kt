package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeCountry(
    val name: String?,
    val code: String?,
    val timezone: String?,
)