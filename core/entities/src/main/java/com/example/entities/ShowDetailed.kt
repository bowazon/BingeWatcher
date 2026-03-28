package com.example.entities

data class ShowDetailed(
    val summary: ShowSummary,
    val ended: String, // empty if still active
    val officialSite: String,
    val imdb: String,
    val largeImage: String // TODO preload bitmap???
)