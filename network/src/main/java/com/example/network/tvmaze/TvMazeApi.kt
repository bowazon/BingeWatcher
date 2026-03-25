package com.example.network.tvmaze

import com.example.network.tvmaze.models.Show


interface TvMazeApi {
    suspend fun search(query: String): Result<List<Show>>
}
