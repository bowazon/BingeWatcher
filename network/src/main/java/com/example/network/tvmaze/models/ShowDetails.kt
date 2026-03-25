package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class ShowDetails(
    val id: Int,
    val url: String,
) {
}