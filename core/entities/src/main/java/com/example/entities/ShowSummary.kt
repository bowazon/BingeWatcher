package com.example.entities

data class ShowSummary(
    val name: String,
    val premiered: String,
    val image: String, // TODO maybe bitmap?
    val genres: String,
    val rating: String
)