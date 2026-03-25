package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeShowDetails(
    val id: Int,
    val url: String,
    val name: String,
    val type: String,
    val language: String,
    val genres: List<String>,
    val status: String,
    val runtime: Long?,
    val averageRuntime: Long,
    val premiered: String,
    val ended: String,
    val officialSite: String?,
    val schedule: TvMazeSchedule,
    val rating: TvMazeRating,
    val weight: Long,
    val network: TvMazeNetwork?,
    val webChannel: TvMazeWebChannel?,
    val dvdCountry: String?,
    val externals: TvMazeExternals,
    val image: TvMazeImage,
    val summary: String?,
    val updated: Long,
    //@JsonProperty("_links")
    //val links: Links,
) {
}