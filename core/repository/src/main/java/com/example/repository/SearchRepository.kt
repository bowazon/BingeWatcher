package com.example.repository

import com.example.entities.ShowDetailed

interface SearchRepository {
    suspend fun searchShow(name: String): List<ShowDetailed>
}