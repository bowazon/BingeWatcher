package com.bowazon.bingewatcher.core.entities

import kotlinx.serialization.Serializable

//TODO remove serialization
@Serializable
data class ActorSummary(
    val id: Int,
    val name: String,
    val image: String,
    val characterName: String
)
