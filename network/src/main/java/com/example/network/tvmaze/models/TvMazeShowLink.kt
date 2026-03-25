package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeShowLink(
    val href: String,
    val name: String,
)