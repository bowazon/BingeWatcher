package com.example.repository

import com.example.entities.ShowDetailed
import com.example.network.tvmaze.TvMazeRemoteDataSource
import com.example.network.tvmaze.mappers.toShowDetailed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    val api: TvMazeRemoteDataSource
) : ShowRepository {
    override suspend fun searchShow(name: String): List<ShowDetailed> {
        // TODO error processing
        return api.search(name).map { it.show.toShowDetailed() }
    }
}