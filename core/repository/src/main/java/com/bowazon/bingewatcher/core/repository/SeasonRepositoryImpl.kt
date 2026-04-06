package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.Episode
import com.bowazon.bingewatcher.core.entities.Season
import com.bowazon.bingewatcher.core.network.tvmaze.TvMazeRemoteDataSource
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toEpisode
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toSeason
import javax.inject.Inject

class SeasonRepositoryImpl @Inject constructor(
    val api: TvMazeRemoteDataSource
) : SeasonRepository {
    override suspend fun getSeasonInfo(
        showId: Int,
        seasonId: Int
    ): Season {
        return api.seasons(showId).first { it.id.toInt() == seasonId }.toSeason()
    }

    override suspend fun getEpisodes(seasonId: Int): List<Episode> {
        return api.episodesInSeason(seasonId).map { it.toEpisode() }
    }

}