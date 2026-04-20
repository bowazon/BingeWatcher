package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.entities.ShowSummary

interface DiscoverRepository {
    suspend fun showsBasedOnLikes(): List<ShowDetailed> // TODO make Flow?
    suspend fun showsPopularNow(): List<ShowDetailed>
    suspend fun episodesThisWeek()
    suspend fun topRatedInGenre(): List<ShowDetailed>
}