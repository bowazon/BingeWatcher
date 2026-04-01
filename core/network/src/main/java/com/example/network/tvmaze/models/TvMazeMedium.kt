package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeMedium(
    val url: String? = null,
    val width: Long? = null,
    val height: Long? = null,
)
