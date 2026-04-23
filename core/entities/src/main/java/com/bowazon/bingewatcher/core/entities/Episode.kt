package com.bowazon.bingewatcher.core.entities

data class Episode(
    val id: Int,
    val name: String,
    val season: Int,
    val episode: Int,
    val airdate: String,
    val runtime: Int,
    val rating: Double?,
    val image: String,
    val summary: String,
    val showName: String,
    val watched: Boolean = false,
    val isUpdatingWatched: Boolean = false, // TODO should really be in UI model
    val followed: Boolean = false
) : CrewSource

data class EpisodeWithShow(
    val episode: Episode,
    val showGenres: List<String> = emptyList(),
    val showImage: String = "",
    val showRating: Double? = null,
    val showId: Int = -1
)