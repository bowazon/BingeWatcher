package com.example.entities

import kotlinx.serialization.Serializable

@Serializable
data class ShowDetailed(
    val id: String,
    val summary: ShowSummary,
    val ended: String, // empty if still active
    val officialSite: String,
    val imdb: String,
    val largeImage: String, // TODO preload bitmap???
    val description: String
)