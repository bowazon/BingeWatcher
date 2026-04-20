package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.ActorSummary
import com.bowazon.bingewatcher.core.entities.CrewSummary
import com.bowazon.bingewatcher.core.entities.Season
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.entities.ShowSummary
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.IMDB_ADDRESS
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toShowSummary
import kotlinx.coroutines.flow.Flow

interface ShowRepository {
    suspend fun getShow(showId: Int): ShowDetailed
    suspend fun getActorsForShow(showId: Int): List<ActorSummary>
    suspend fun getCrewForShow(showId: Int): List<CrewSummary>
    suspend fun getSeasonsForShow(showId: Int): List<Season>
    suspend fun getImagesForShow(showId: Int): List<String>
    suspend fun toggleFollowShow(show: ShowDetailed)
    fun observeFollowed(showId: Int): Flow<Boolean> // TODO or use id?

    // TODO delete all from above? (except toggleFollowShow)

    fun observeShow(showId: Int): Flow<ShowTempModel>
}

data class ShowTempModel(
    val id: Int,
    val summary: ShowSummary,
    val ended: String, // empty if still active
    val officialSite: String,
    val imdb: String,
    val largeImage: String,
    val description: String,
    val actors: List<ActorSummary>,
    val seasons: List<Season>,
    val images: List<String>,
    val followed: Boolean
)

fun ShowTempModel.toShowDetailed() : ShowDetailed {
    return ShowDetailed (
        id = id,
        summary = summary,
        ended = ended,
        officialSite = officialSite, // TODO thing to consider: what if official site of show changed remotely, but we always having deal with this recorded official site?
        imdb = imdb,
        largeImage = largeImage,
        description = description
    )
}