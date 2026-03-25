package com.example.network.tvmaze.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvMazeShow(
    val score: Float,

    @SerialName("show")
    val showDetails: TvMazeShowDetails,
) {

}