package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.CrewSource
import com.bowazon.bingewatcher.core.entities.CrewSummary
import com.bowazon.bingewatcher.core.entities.FullCrew

interface FullCrewRepository {
    suspend fun getFullCrew(request: FullCrewRequest) : FullCrew<CrewSource>
}

sealed interface FullCrewRequest {
    data class Show(val showId: Int) : FullCrewRequest
    data class Episode(val episodeId: Int) : FullCrewRequest
}