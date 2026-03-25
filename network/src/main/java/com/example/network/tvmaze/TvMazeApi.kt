package com.example.network.tvmaze

import com.example.network.tvmaze.TvMazeApi.Companion.BASE_URL
import com.example.network.tvmaze.models.Show
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TvMazeApi {
    @GET("search/shows")
    suspend fun search(@Query("q") query: String): Result<List<Show>>

    // TODO move the shit out of here
    companion object {
        const val BASE_URL = "https://api.tvmaze.com/"
    }
}

val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

val retrofit = Retrofit.Builder()
    .client(client)
    .addCallAdapterFactory(ResultCallAdapterFactory.create())
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

val api = retrofit.create(TvMazeApi::class.java)