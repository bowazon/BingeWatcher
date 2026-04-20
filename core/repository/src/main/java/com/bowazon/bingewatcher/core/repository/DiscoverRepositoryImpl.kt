package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.database.BingeWatcherDatabase
import com.bowazon.bingewatcher.core.entities.ShowSummary
import com.bowazon.bingewatcher.core.network.tvmaze.retrofit.TvMazeRetrofitApi

class DiscoverRepositoryImpl(
    val api: TvMazeRetrofitApi,
    val database: BingeWatcherDatabase,
) : DiscoverRepository {
    override suspend fun showsBasedOnLikes(): List<ShowSummary> {
        TODO("Not yet implemented")
    }

    override suspend fun showsPopularNow(): List<ShowSummary> {
        TODO("Not yet implemented")
    }

    override suspend fun episodesThisWeek() {
        TODO("Not yet implemented")
    }

    override suspend fun topRatedInGenre(): List<ShowSummary> {
        TODO("Not yet implemented")
    }
}