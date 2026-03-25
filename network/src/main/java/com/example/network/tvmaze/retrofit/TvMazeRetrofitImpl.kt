package com.example.network.tvmaze.retrofit


import com.example.network.tvmaze.TvMazeRemoteDataSource
import com.example.network.tvmaze.models.TvMazeActor
import com.example.network.tvmaze.models.TvMazeCastCredit
import com.example.network.tvmaze.models.TvMazeCrewCredit
import com.example.network.tvmaze.models.TvMazeCrewMember
import com.example.network.tvmaze.models.TvMazeEpisode
import com.example.network.tvmaze.models.TvMazePersonDetails
import com.example.network.tvmaze.models.TvMazeSeason
import com.example.network.tvmaze.models.TvMazeShow
import com.example.network.tvmaze.models.TvMazeShowDetails
import com.example.network.tvmaze.models.TvMazeShowImage
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


// TODO what should be constructor injected - OkHttpClient,
// Json, Retrofit? That probably depends on how we should test this
class TvMazeRetrofitImpl : TvMazeRemoteDataSource {

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

    override suspend fun search(query: String): Result<List<TvMazeShow>> {
        return api.search(query)
    }

    override suspend fun singleSearch(query: String): Result<TvMazeShowDetails> {
        return api.singleSearch(query)
    }

    override suspend fun peopleSearch(query: String): Result<List<TvMazePersonDetails>> {
        return api.peopleSearch(query)
    }

    override suspend fun webSchedule(
        country: String,
        date: String
    ): Result<List<TvMazeEpisode>> {
        return api.webSchedule(country, date)
    }

    override suspend fun fullSchedule(): Result<List<TvMazeEpisode>> {
        return api.fullSchedule()
    }

    override suspend fun showInfo(showId: Int): Result<TvMazeShowDetails> {
        return api.showInfo(showId)
    }

    override suspend fun episodeList(showId: Int): Result<List<TvMazeEpisode>> {
        return api.episodeList(showId)
    }

    override suspend fun episodeByNumber(
        showId: Int,
        s: String,
        e: String
    ): Result<TvMazeEpisode> {
        return api.episodeByNumber(showId, s, e)
    }

    override suspend fun episodeByDate(
        showId: Int,
        date: String
    ): Result<TvMazeEpisode> {
        return api.episodeByDate(showId, date)
    }

    override suspend fun seasons(showId: Int): Result<TvMazeSeason> {
        return api.seasons(showId)
    }

    override suspend fun cast(showId: Int): Result<List<TvMazeActor>> {
        return api.cast(showId)
    }

    override suspend fun crew(showId: Int): Result<List<TvMazeCrewMember>> {
        return api.crew(showId)
    }

    override suspend fun images(showId: Int): Result<List<TvMazeShowImage>> {
        return api.images(showId)
    }

    override suspend fun showsPaginated(page: Int): Result<List<TvMazeShowDetails>> {
        return api.showsPaginated(page)
    }

    override suspend fun episode(id: Int): Result<TvMazeEpisode> {
        return api.episode(id)
    }

    override suspend fun personCastCredits(id: Int): Result<List<TvMazeCastCredit>> {
        return api.personCastCredits(id)
    }

    override suspend fun personCrewCredits(id: Int): Result<List<TvMazeCrewCredit>> {
        return api.personCrewCredits(id)
    }

    // TODO move the shit out of here
    companion object {
        const val BASE_URL = "https://api.tvmaze.com/"
    }
}

