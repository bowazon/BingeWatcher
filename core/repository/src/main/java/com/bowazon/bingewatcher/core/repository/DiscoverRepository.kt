package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.ShowSummary

interface DiscoverRepository {
    suspend fun showsBasedOnLikes(): List<ShowSummary>
    suspend fun showsPopularNow(): List<ShowSummary>
    suspend fun episodesThisWeek()
    suspend fun topRatedInGenre(): List<ShowSummary>
}