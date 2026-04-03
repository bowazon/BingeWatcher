package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.Episode
import com.bowazon.bingewatcher.core.entities.Season

interface SeasonRepository {
    // There is no API call for season info, we can only load
    // show info and find season there
    suspend fun getSeasonInfo(showId: Int, seasonId: Int): Season
    suspend fun getEpisodes(seasonId: Int): List<Episode>
}