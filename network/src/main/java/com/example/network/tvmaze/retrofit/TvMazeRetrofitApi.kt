package com.example.network.tvmaze.retrofit

import com.example.network.tvmaze.models.Show
import retrofit2.http.GET
import retrofit2.http.Query

interface TvMazeRetrofitApi {
    @GET("search/shows")
    suspend fun search(@Query("q") query: String): Result<List<Show>>
}
