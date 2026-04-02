package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.Actor
import com.bowazon.bingewatcher.core.entities.Season
import com.bowazon.bingewatcher.core.entities.ShowDetailed

interface ShowRepository {
    suspend fun getShow(showId: Int): ShowDetailed
    suspend fun getActorsForShow(showId: Int): List<Actor>
    suspend fun getSeasonsForShow(showId: Int): List<Season>
    suspend fun getImagesForShow(showId: Int): List<String>
}