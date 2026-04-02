package com.example.network.tvmaze.mappers

import com.example.entities.Actor
import com.example.entities.Season
import com.example.entities.ShowDetailed
import com.example.entities.ShowSummary
import com.example.network.tvmaze.models.TvMazeActor
import com.example.network.tvmaze.models.TvMazeCharacter
import com.example.network.tvmaze.models.TvMazeImage
import com.example.network.tvmaze.models.TvMazeNetwork
import com.example.network.tvmaze.models.TvMazePerson
import com.example.network.tvmaze.models.TvMazeResolutions
import com.example.network.tvmaze.models.TvMazeSeason
import com.example.network.tvmaze.models.TvMazeShow
import com.example.network.tvmaze.models.TvMazeShowImage
import kotlin.String
import kotlin.text.substringBefore

const val IMDB_ADDRESS = "https://www.imdb.com/title/"

fun TvMazeShow.toShowSummary(): ShowSummary {
    return ShowSummary(
        name = name,
        premiered = premiered?.substringBefore("-") ?: "",
        image = image?.medium  ?: "" ,
        genres = genres?.joinToString(separator = "/")  ?: "",
        rating = rating?.average?.let { it.toString() } ?: ""
    )
}

fun TvMazeShow.toShowDetailed(): ShowDetailed {
    return ShowDetailed(
        id = id,
        summary = toShowSummary(),
        ended = ended?.substringBefore("-") ?: "", // empty if still active
        officialSite = officialSite ?: "",
        imdb = IMDB_ADDRESS + (externals?.imdb ?: ""),
        largeImage = image?.original ?: "",
        description = summary ?: ""
    )
}

fun TvMazeActor.toActor(): Actor {
    return Actor(
        id = person.id.toInt(),
        name = person.name,
        image = person.image?.medium ?: "",
        characterName = character.name
    )
}

fun TvMazeSeason.toSeason(): Season {
    return Season (
        id = id.toInt(),
        episodesAmount = episodeOrder?.toInt() ?: 0,
        image = image?.medium ?: "",
        premiereDate = premiereDate.substringBefore("-")
    )
}

fun TvMazeShowImage.toImageUrl(): String? {
    return resolutions?.original?.url
}
