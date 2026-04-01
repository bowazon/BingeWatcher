package com.example.repository

import com.example.entities.Actor
import com.example.entities.Season
import com.example.entities.ShowDetailed

interface ShowRepository {

    // TODO or should it be in SEARCH REPOSITORY?
    // that is, repository for search screen
    suspend fun searchShow(name: String): List<ShowDetailed>

    suspend fun getActorsForShow(showId: Int): List<Actor>
    suspend fun getSeasonsForShow(showId: Int): List<Season>
    suspend fun getImagesForShow(showId: Int): List<String>
}