package com.example.network.tvmaze.retrofit

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
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// https://www.tvmaze.com/api
interface TvMazeRetrofitApi {
    // search
    @GET("search/shows")
    suspend fun search(@Query("q") query: String): Result<List<TvMazeShow>>

    @GET("singlesearch/shows")
    suspend fun singleSearch(@Query("q") query: String): Result<TvMazeShowDetails>

    /*
    @GET("lookup/shows")
    suspend fun lookup(@Query("thetvdb") query: String): Result<TvMazeShowDetails>
     */

    @GET("search/people")
    suspend fun peopleSearch(@Query("q") query: String): Result<List<TvMazePersonDetails>>


    // schedule
    @GET("schedule/web")
    suspend fun webSchedule(
        @Query("country") countryCode: String,
        @Query("date") date: String
    ): Result<List<TvMazeEpisode>>

    @GET("schedule/full")
    suspend fun fullSchedule(): Result<List<TvMazeEpisode>>


    // shows
    @GET("shows/{id}")
    suspend fun showInfo(@Path("id") showId: Int): Result<TvMazeShowDetails>

    @GET("shows/{id}/episodes")
    suspend fun episodeList(@Path("id") showId: Int): Result<List<TvMazeEpisode>>

    @GET("shows/{id}/episodebynumber")
    suspend fun episodeByNumber(
        @Path("id") showId: Int,
        @Query("season") s: String,
        @Query("number") e: String
    ): Result<TvMazeEpisode>

    @GET("shows/{id}/episodesbydate")
    suspend fun episodeByDate(
        @Path("id") showId: Int,
        @Query("date") date: String
    ): Result<TvMazeEpisode>

    @GET("shows/{id}/seasons")
    suspend fun seasons(@Path("id") showId: Int): Result<TvMazeSeason>

    @GET("shows/{id}/cast")
    suspend fun cast(@Path("id") showId: Int): Result<List<TvMazeActor>>

    @GET("shows/{id}/crew")
    suspend fun crew(@Path("id") showId: Int): Result<List<TvMazeCrewMember>>

    @GET("shows/{id}/images")
    suspend fun images(@Path("id") showId: Int): Result<List<TvMazeShowImage>>

    @GET("shows")
    suspend fun showsPaginated(@Query("page") page: Int): Result<List<TvMazeShowDetails>>

    // Episodes
    @GET("episodes/{id}")
    suspend fun episode(@Path("id") id: Int): Result<TvMazeEpisode>


    // people
    @GET("people/{id}/castcredits")
    suspend fun personCastCredits(@Path("id") id: Int): Result<List<TvMazeCastCredit>>

    @GET("people/{id}/crewcredits")
    suspend fun personCrewCredits(@Path("id") id: Int): Result<List<TvMazeCrewCredit>>
}
