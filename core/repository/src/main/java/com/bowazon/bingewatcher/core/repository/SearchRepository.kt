package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.ShowDetailed

interface SearchRepository {
    suspend fun searchShow(name: String): List<ShowDetailed>
}