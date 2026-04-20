package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.database.BingeWatcherDatabase
import com.bowazon.bingewatcher.core.database.models.toShowDbo
import com.bowazon.bingewatcher.core.entities.ActorSummary
import com.bowazon.bingewatcher.core.entities.CrewSummary
import com.bowazon.bingewatcher.core.entities.Season
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.network.tvmaze.TvMazeRemoteDataSource
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toActor
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toCrew
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toImageUrl
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toSeason
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toShowDetailed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.String

class ShowRepositoryImpl @Inject constructor(
    val api: TvMazeRemoteDataSource,
    val database: BingeWatcherDatabase,
) : ShowRepository {

    override suspend fun getShow(showId: Int): ShowDetailed {
        return api.showInfo(showId).toShowDetailed()
    }

    override suspend fun getActorsForShow(showId: Int): List<ActorSummary> {
        return api.cast(showId).map { it.toActor() }
    }

    override suspend fun getCrewForShow(showId: Int): List<CrewSummary> {
        return api.crew(showId).map { it.toCrew() }
    }

    override suspend fun getSeasonsForShow(showId: Int): List<Season> {
        return api.seasons(showId).map { it.toSeason("") } // TODO this season and season with show name should be separate models probably
    }

    override suspend fun getImagesForShow(showId: Int): List<String> {
        return api.images(showId).mapNotNull { it.toImageUrl() }
    }

    override suspend fun toggleFollowShow(show: ShowDetailed) {
        if (!database.showDao().isShowSaved(show.id)) {
            database.showDao().insert(listOf(show.toShowDbo()))
        } else {
            database.showDao().delete(show.toShowDbo())
        }
    }

    override fun observeFollowed(showId: Int): Flow<Boolean> {
        return database.showDao().observeShowSaved(showId)
    }

    override fun observeShow(showId: Int): Flow<ShowTempModel> {
        return combine(
            flow { emit(api.showInfo(showId).toShowDetailed()) },
            flow { emit(api.cast(showId).map { it.toActor() }) },
            flow { emit(api.seasons(showId).map { it.toSeason("") }) },
            flow { emit(api.images(showId).mapNotNull { it.toImageUrl() }) },
            database.showDao().observeShowSaved(showId)
        ) {
            show, cast, seasons, images, followed ->
            ShowTempModel(
                id = show.id,
                summary = show.summary,
                ended = show.ended,
                officialSite = show.officialSite,
                imdb = show.imdb,
                largeImage = show.largeImage,
                description = show.description,
                actors = cast,
                seasons = seasons,
                images = images,
                followed = followed
            )
        }
    }
}