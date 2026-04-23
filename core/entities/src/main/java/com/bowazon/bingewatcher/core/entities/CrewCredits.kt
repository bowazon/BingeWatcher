package com.bowazon.bingewatcher.core.entities

data class CrewCredits(
    val type: String,
    val showId: Int,
    val showName: String,
    val showPremiered: String,
    val showGenre: String,
    val showPicture: String,
    val showRating: Double?,
)