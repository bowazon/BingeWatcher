package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.Episode
import com.bowazon.bingewatcher.core.entities.Season
import com.bowazon.bingewatcher.core.network.tvmaze.TvMazeRemoteDataSource
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toEpisode
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toSeason
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class SeasonRepositoryImpl @Inject constructor(
    val api: TvMazeRemoteDataSource
) : SeasonRepository {
    override suspend fun getSeasonInfo(
        showId: Int,
        seasonId: Int
    ): Season {
        val showName = api.showInfo(showId).name
        val season = api.seasons(showId).first { it.id.toInt() == seasonId }.toSeason(showName)

        return season
        // TODO I know it's stupid - two consecutive suspend calls.
        // Season info doesn't have show info like sho name.
        // I will fix this when I'll fix API model problem with embedded field
    }

    override suspend fun getEpisodes(seasonId: Int): List<Episode> {
        return api.episodesInSeason(seasonId).map { it.toEpisode() }
    }

}