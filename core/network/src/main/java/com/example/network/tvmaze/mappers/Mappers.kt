package com.example.network.tvmaze.mappers

import com.example.entities.ShowDetailed
import com.example.entities.ShowSummary
import com.example.network.tvmaze.models.TvMazeShow
import kotlin.String
import kotlin.text.substringBefore

fun TvMazeShow.toShowSummary(): ShowSummary {
    return ShowSummary(
        name = name,
        premiered = premiered?.substringBefore("-") ?: "",
        image = image?.medium  ?: "" ,
        genres = genres?.joinToString(separator = "/")  ?: "",
        rating = rating?.average.toString()  ?: ""
    )
}

fun TvMazeShow.toShowDetailed(): ShowDetailed {
    return ShowDetailed(
        summary = toShowSummary(),
        ended = ended?.substringBefore("-") ?: "", // empty if still active
        officialSite = officialSite ?: "",
        imdb = externals?.imdb ?: "",
        largeImage = image?.original ?: ""
    )
}