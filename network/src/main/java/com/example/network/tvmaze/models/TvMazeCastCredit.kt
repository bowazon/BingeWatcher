package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeCastCredit(
    val self: Boolean,
    val voice: Boolean,
    //@JsonProperty("_links")
    val links: TvMazeActorAssociatedWith,
)