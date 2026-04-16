package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.CrewSummary

interface FullCrewRepository {
    suspend fun getFullCrew(request: FullCrewRequest) : List<CrewSummary>
}

sealed interface FullCrewRequest {
    data class Show(val showId: Int) : FullCrewRequest
    data class Episode(val episodeId: Int) : FullCrewRequest
}