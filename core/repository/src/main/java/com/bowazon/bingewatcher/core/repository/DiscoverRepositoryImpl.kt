package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.database.BingeWatcherDatabase
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.entities.ShowSummary
import com.bowazon.bingewatcher.core.network.tvmaze.TvMazeRemoteDataSource
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toShowDetailed
import com.bowazon.bingewatcher.core.network.tvmaze.retrofit.TvMazeRetrofitApi

class DiscoverRepositoryImpl(
    val api: TvMazeRemoteDataSource,
    val database: BingeWatcherDatabase,
) : DiscoverRepository {
    override suspend fun showsBasedOnLikes(): List<ShowDetailed> {
        val likedShows = database.showDao().getAll()
        val favoriteGenres = likedShows.flatMap { it.genres }

        val allShows = mutableListOf<ShowDetailed>()

        for (page in 0..5) {
            allShows.addAll(api.showsPaginated(page).map { it.toShowDetailed() })
        }

        val filtered = allShows.filter { show ->
            show.summary.genres.any { it in favoriteGenres }
        }

        val grouped = filtered
            .flatMap { show ->
                show.summary.genres
                    .filter { it in favoriteGenres }
                    .map { genre -> genre to show }
            }
            .groupBy({ it.first }, { it.second })

        val topPerGenre = grouped.mapValues { (_, shows) ->
            shows
                .filter { it.summary.rating != "" }
                .maxByOrNull { it.summary.rating.toFloat() }
        }

        return topPerGenre.values
            .filterNotNull()
            .distinctBy { it.id }
            .take(5)
    }

    override suspend fun showsPopularNow(): List<ShowDetailed> {
        TODO("Not yet implemented")
    }

    override suspend fun episodesThisWeek() {
        TODO("Not yet implemented")
    }

    override suspend fun topRatedInGenre(): List<ShowDetailed> {
        TODO("Not yet implemented")
    }
}