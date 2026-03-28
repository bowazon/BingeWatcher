package com.example.repository

import com.example.entities.ShowDetailed

interface ShowRepository {
    suspend fun searchShow(name: String): List<ShowDetailed>
}