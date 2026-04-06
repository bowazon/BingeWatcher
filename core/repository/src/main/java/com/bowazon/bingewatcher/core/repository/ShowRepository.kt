package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.ActorSummary
import com.bowazon.bingewatcher.core.entities.CrewSummary
import com.bowazon.bingewatcher.core.entities.Season
import com.bowazon.bingewatcher.core.entities.ShowDetailed

interface ShowRepository {
    suspend fun getShow(showId: Int): ShowDetailed
    suspend fun getActorsForShow(showId: Int): List<ActorSummary>
    suspend fun getCrewForShow(showId: Int): List<CrewSummary>
    suspend fun getSeasonsForShow(showId: Int): List<Season>
    suspend fun getImagesForShow(showId: Int): List<String>
}