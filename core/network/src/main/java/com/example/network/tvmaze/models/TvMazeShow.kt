package com.example.network.tvmaze.models

import kotlinx.serialization.Serializable

@Serializable
data class TvMazeShow(
    val id: Int,
    val url: String,
    val name: String,
    val type: String? = null,
    val language: String? = null,
    val genres: List<String>? = null,
    val status: String? = null,
    val runtime: Long? = null,
    val averageRuntime: Long? = null,
    val premiered: String? = null,
    val ended: String? = null,
    val officialSite: String? = null,
    val schedule: TvMazeSchedule? = null,
    val rating: TvMazeRating? = null,
    val weight: Long? = null,
    val network: TvMazeNetwork? = null,
    val webChannel: TvMazeWebChannel? = null,
    val dvdCountry: TvMazeCountry? = null,
    val externals: TvMazeExternals? = null,
    // TODO can be null
    val image: TvMazeImage? = null,
    val summary: String? = null,
    val updated: Long? = null,
    //@JsonProperty("_links")
    //val links: Links,
) {
}