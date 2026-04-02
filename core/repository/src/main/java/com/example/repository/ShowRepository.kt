package com.example.repository

import com.example.entities.Actor
import com.example.entities.Season
import com.example.entities.ShowDetailed

interface ShowRepository {
    suspend fun getShow(showId: Int): ShowDetailed
    suspend fun getActorsForShow(showId: Int): List<Actor>
    suspend fun getSeasonsForShow(showId: Int): List<Season>
    suspend fun getImagesForShow(showId: Int): List<String>
}