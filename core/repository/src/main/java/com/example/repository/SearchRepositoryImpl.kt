package com.example.repository

import com.example.entities.ShowDetailed
import com.example.network.tvmaze.TvMazeRemoteDataSource
import com.example.network.tvmaze.mappers.toShowDetailed
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    val api: TvMazeRemoteDataSource
) : SearchRepository {
    override suspend fun searchShow(name: String): List<ShowDetailed> {
        // TODO error processing
        return api.search(name).map { it.show.toShowDetailed() }
    }
}