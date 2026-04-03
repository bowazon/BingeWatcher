package com.bowazon.bingewatcher.core.network.tvmaze.retrofit

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
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// https://www.tvmaze.com/api
interface TvMazeRetrofitApi {
    // search
    @GET("search/shows")
    suspend fun search(@Query("q") query: String): List<TvMazeScoredShow>

    @GET("singlesearch/shows")
    suspend fun singleSearch(@Query("q") query: String): TvMazeShow

    /*
    @GET("lookup/shows")
    suspend fun lookup(@Query("thetvdb") query: String): TvMazeShowDetails
     */

    @GET("search/people")
    suspend fun peopleSearch(@Query("q") query: String): List<TvMazeScoredPerson>


    // schedule
    @GET("schedule/web")
    suspend fun webSchedule(
        @Query("country") countryCode: String,
        @Query("date") date: String
    ): List<TvMazeEpisode>

    @GET("schedule/full")
    suspend fun fullSchedule(): List<TvMazeEpisode>


    // shows
    @GET("shows/{id}")
    suspend fun showInfo(@Path("id") showId: Int): TvMazeShow

    @GET("shows/{id}/episodes")
    suspend fun episodeList(@Path("id") showId: Int): List<TvMazeEpisode>

    @GET("shows/{id}/episodebynumber")
    suspend fun episodeByNumber(
        @Path("id") showId: Int,
        @Query("season") s: String,
        @Query("number") e: String
    ): TvMazeEpisode

    @GET("shows/{id}/episodesbydate")
    suspend fun episodeByDate(
        @Path("id") showId: Int,
        @Query("date") date: String
    ): TvMazeEpisode

    @GET("shows/{id}/seasons")
    suspend fun seasons(@Path("id") showId: Int): List<TvMazeSeason>

    @GET("shows/{id}/cast")
    suspend fun cast(@Path("id") showId: Int): List<TvMazeActor>

    @GET("shows/{id}/crew")
    suspend fun crew(@Path("id") showId: Int): List<TvMazeCrewMember>

    @GET("shows/{id}/images")
    suspend fun images(@Path("id") showId: Int): List<TvMazeShowImage>

    @GET("shows")
    suspend fun showsPaginated(@Query("page") page: Int): List<TvMazeShow>

    // Episodes
    @GET("episodes/{id}")
    suspend fun episode(@Path("id") id: Int): TvMazeEpisode


    // people
    @GET("people/{id}")
    suspend fun personInfo(@Path("id") id: Int): TvMazePerson

    @GET("people/{id}/castcredits")
    suspend fun personCastCredits(@Path("id") id: Int): List<TvMazeCastCredit>

    @GET("people/{id}/crewcredits")
    suspend fun personCrewCredits(@Path("id") id: Int): List<TvMazeCrewCredit>
}
