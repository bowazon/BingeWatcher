package com.bowazon.bingewatcher.core.network.tvmaze.retrofit


import com.bowazon.bingewatcher.core.network.tvmaze.TvMazeRemoteDataSource
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeActor
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeCastCredit
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeCrewCredit
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeCrewMember
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeEpisode
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazePerson
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeScoredPerson
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeScoredShow
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeSeason
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeShow
import com.bowazon.bingewatcher.core.network.tvmaze.models.TvMazeShowImage
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
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    val api = retrofit.create(TvMazeRetrofitApi::class.java)

    override suspend fun search(query: String): List<TvMazeScoredShow> {
        return api.search(query)
    }

    override suspend fun singleSearch(query: String): TvMazeShow {
        return api.singleSearch(query)
    }

    override suspend fun peopleSearch(query: String): List<TvMazeScoredPerson> {
        return api.peopleSearch(query)
    }

    override suspend fun webSchedule(
        country: String,
        date: String
    ): List<TvMazeEpisode> {
        return api.webSchedule(country, date)
    }

    override suspend fun fullSchedule(): List<TvMazeEpisode> {
        return api.fullSchedule()
    }

    override suspend fun showInfo(showId: Int): TvMazeShow {
        return api.showInfo(showId)
    }

    override suspend fun episodeList(showId: Int): List<TvMazeEpisode> {
        return api.episodeList(showId)
    }

    override suspend fun episodeByNumber(
        showId: Int,
        s: String,
        e: String
    ): TvMazeEpisode {
        return api.episodeByNumber(showId, s, e)
    }

    override suspend fun episodeByDate(
        showId: Int,
        date: String
    ): TvMazeEpisode {
        return api.episodeByDate(showId, date)
    }

    override suspend fun seasons(showId: Int): List<TvMazeSeason> {
        return api.seasons(showId)
    }

    override suspend fun cast(showId: Int): List<TvMazeActor> {
        return api.cast(showId)
    }

    override suspend fun crew(showId: Int): List<TvMazeCrewMember> {
        return api.crew(showId)
    }

    override suspend fun images(showId: Int): List<TvMazeShowImage> {
        return api.images(showId)
    }

    override suspend fun showsPaginated(page: Int): List<TvMazeShow> {
        return api.showsPaginated(page)
    }

    override suspend fun episode(id: Int): TvMazeEpisode {
        return api.episode(id)
    }

    override suspend fun personInfo(id: Int): TvMazePerson {
        return api.personInfo(id)
    }

    override suspend fun personCastCredits(id: Int): List<TvMazeCastCredit> {
        return api.personCastCredits(id)
    }

    override suspend fun personCrewCredits(id: Int): List<TvMazeCrewCredit> {
        return api.personCrewCredits(id)
    }

    // TODO move the shit out of here
    companion object {
        const val BASE_URL = "https://api.tvmaze.com/"
    }
}

