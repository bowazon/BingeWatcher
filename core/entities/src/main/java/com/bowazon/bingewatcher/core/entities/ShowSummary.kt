package com.bowazon.bingewatcher.core.entities

import kotlinx.serialization.Serializable

@Serializable
data class ShowSummary(
    val name: String,
    val premiered: String,
    val image: String, // TODO maybe bitmap?
    val genres: String,
    val rating: String
)