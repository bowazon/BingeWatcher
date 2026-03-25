package com.example.network.tvmaze.retrofit


import com.example.network.tvmaze.TvMazeApi
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


// TODO what should be constructor injected - OkHttpClient,
// Json, Retrofit? That probably depends on how we should test this
class TvMazeRetrofitImpl : TvMazeApi {

    val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            // TODO only in debug mode
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    val retrofit = Retrofit.Builder()
        .client(client)
        // TODO maybe use custom Result instead of Kotlin Result
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    val api = retrofit.create(TvMazeRetrofitApi::class.java)

    override suspend fun search(query: String): Result<List<Show>> {
        return api.search(query)
    }

    // TODO move the shit out of here
    companion object {
        const val BASE_URL = "https://api.tvmaze.com/"
    }
}

