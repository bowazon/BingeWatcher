package com.bowazon.bingewatcher.core.entities

import kotlinx.serialization.Serializable

@Serializable
data class Actor(
    val id: Int,
    val name: String,
    val image: String,
    val characterName: String
)
