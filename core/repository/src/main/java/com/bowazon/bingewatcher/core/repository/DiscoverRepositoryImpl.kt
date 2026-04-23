package com.bowazon.bingewatcher.core.repository

import android.util.Log
import com.bowazon.bingewatcher.core.database.BingeWatcherDatabase
import com.bowazon.bingewatcher.core.entities.Episode
import com.bowazon.bingewatcher.core.entities.EpisodeWithShow
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.entities.ShowSummary
import com.bowazon.bingewatcher.core.network.tvmaze.TvMazeRemoteDataSource
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toEpisode
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toShowDetailed
import com.bowazon.bingewatcher.core.network.tvmaze.retrofit.TvMazeRetrofitApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class DiscoverRepositoryImpl(
    val api: TvMazeRemoteDataSource,
    val database: BingeWatcherDatabase,
) : DiscoverRepository {
    override fun showsBasedOnLikes(): Flow<List<ShowDetailed>> {
        val likedFlow = database.showDao().observeAll()

        return likedFlow.flatMapLatest { likedShows ->

            val favoriteGenres = likedShows
                .flatMap { it.genres }
                .filter { it.isNotBlank() }
                .toSet()

            flow {

                val allShows = mutableListOf<ShowDetailed>()

                for (page in 0..10) {
                    val shows = api.showsPaginated(page)
                        .map { it.toShowDetailed() }

                    allShows += shows
                }

                val filtered = allShows.filter { show ->
                    show.summary.genres.any { it in favoriteGenres }
                }

                val grouped = filtered
                    .flatMap { show ->
                        show.summary.genres
                            .filter { it in favoriteGenres }
                            .map { it to show }
                    }
                    .groupBy({ it.first }, { it.second })

                val topPerGenre = grouped.mapValues { (_, shows) ->
                    shows
                        .filter { it.summary.rating != null }
                        .maxByOrNull { it.summary.rating!! }
                }

                emit(
                    topPerGenre.values
                        .filterNotNull()
                        .distinctBy { it.id }
                        .take(20)
                )
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getEpisodeSchedule(date: LocalDate): List<EpisodeWithShow> = coroutineScope {
        val formattedDate = date.toString()
        val schedule = api.webSchedule("US", formattedDate)

        schedule.map {
            val showId = it.links?.show?.href?.substringAfterLast("/")?.toInt()
            if (showId != null) {
                val show = async { api.showInfo(showId).toShowDetailed() }.await()
                EpisodeWithShow(
                    episode = it.toEpisode(),
                    showGenres = show.summary.genres,
                    showImage = show.summary.image,
                    showRating = show.summary.rating,
                    showId = show.id
                )
            } else {
                EpisodeWithShow(
                    episode = it.toEpisode(),
                )
            }
        }.sortedByDescending  { episode ->
            episode.showRating ?: 0.0
        }
    }
}