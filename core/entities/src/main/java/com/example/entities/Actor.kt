package com.example.entities

import kotlinx.serialization.Serializable

@Serializable
data class Actor(
    val id: Int,
    val name: String,
    val image: String,
    val characterName: String
)
