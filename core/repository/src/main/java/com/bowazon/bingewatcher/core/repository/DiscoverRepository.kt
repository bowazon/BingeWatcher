package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.Episode
import com.bowazon.bingewatcher.core.entities.EpisodeWithShow
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.entities.ShowSummary
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DiscoverRepository {
    fun showsBasedOnLikes(): Flow<List<ShowDetailed>>
    suspend fun getEpisodeSchedule(date: LocalDate): List<EpisodeWithShow>
}