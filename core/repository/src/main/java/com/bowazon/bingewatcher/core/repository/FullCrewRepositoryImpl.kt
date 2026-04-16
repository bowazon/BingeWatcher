package com.bowazon.bingewatcher.core.repository

import android.util.Log
import com.bowazon.bingewatcher.core.entities.CrewSource
import com.bowazon.bingewatcher.core.entities.CrewSummary
import com.bowazon.bingewatcher.core.entities.Episode
import com.bowazon.bingewatcher.core.entities.FullCrew
import com.bowazon.bingewatcher.core.entities.ShowSummary
import com.bowazon.bingewatcher.core.network.tvmaze.TvMazeRemoteDataSource
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toActor
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toCrew
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toShowSummary
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class FullCrewRepositoryImpl(
    val api: TvMazeRemoteDataSource
) : FullCrewRepository {
    override suspend fun getFullCrew(request: FullCrewRequest): FullCrew<CrewSource> = coroutineScope {
        when (request) {
            is FullCrewRequest.Show -> {
                val show = async { api.showInfo(request.showId).toShowSummary() }
                val cast = async { api.cast(request.showId).map { it.toCrew() } }
                val crew = async { api.crew(request.showId).map { it.toCrew() } }

                val crewMerged = (crew.await() + cast.await())
                    .groupBy { crewSummary ->
                        crewSummary.name
                    }.map { (_, personsCrewRoles) ->
                        val first = personsCrewRoles.first()

                        val mergedRoles = personsCrewRoles
                            .map { it.role }
                            .distinct()
                            .joinToString("\n")

                        first.copy(role = mergedRoles)
                    }

                FullCrew<ShowSummary>(
                    show.await(),
                    crewMerged
                )
            }
            is FullCrewRequest.Episode -> {
                TODO()
            }
        }
    }
}