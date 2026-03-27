package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeMedium(
    val url: String,
    val width: Long,
    val height: Long,
)
