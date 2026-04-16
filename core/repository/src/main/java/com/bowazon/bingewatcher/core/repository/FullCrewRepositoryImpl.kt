package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.CrewSummary
import com.bowazon.bingewatcher.core.network.tvmaze.TvMazeRemoteDataSource
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toActor
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toCrew
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class FullCrewRepositoryImpl(
    val api: TvMazeRemoteDataSource
) : FullCrewRepository {
    override suspend fun getFullCrew(request: FullCrewRequest): List<CrewSummary> = coroutineScope {
        when (request) {
            is FullCrewRequest.Show -> {
                val cast = async { api.cast(request.showId).map { it.toCrew() } }
                val crew = async { api.crew(request.showId).map { it.toCrew() } }
                cast.await() + crew.await()
            }
            is FullCrewRequest.Episode -> {
                emptyList()
            }
        }
    }
}