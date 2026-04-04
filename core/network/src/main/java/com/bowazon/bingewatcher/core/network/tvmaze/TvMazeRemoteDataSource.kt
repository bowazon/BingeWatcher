package com.bowazon.bingewatcher.core.network.tvmaze

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
import retrofit2.http.Path

interface TvMazeRemoteDataSource {
    // search
    suspend fun search(query: String): List<TvMazeScoredShow>
    suspend fun singleSearch(query: String): TvMazeShow
    //suspend fun lookup(query: String): TvMazeShow
    suspend fun peopleSearch(query: String): List<TvMazeScoredPerson>

    // schedule
    suspend fun webSchedule(country: String, date: String): List<TvMazeEpisode>
    suspend fun fullSchedule(): List<TvMazeEpisode>

    // shows
    suspend fun showInfo(showId: Int): TvMazeShow
    suspend fun episodeList(showId: Int): List<TvMazeEpisode>
    suspend fun episodeByNumber(showId: Int, s: String, e: String): TvMazeEpisode
    suspend fun episodeByDate(showId: Int, date: String): TvMazeEpisode
    suspend fun seasons(showId: Int): List<TvMazeSeason>
    suspend fun cast(showId: Int): List<TvMazeActor>
    suspend fun crew(showId: Int): List<TvMazeCrewMember>
    suspend fun images(showId: Int): List<TvMazeShowImage>
    suspend fun showsPaginated(page: Int): List<TvMazeShow>

    // Seasons
    suspend fun episodesInSeason(id: Int): List<TvMazeEpisode>

    // Episodes
    suspend fun episode(id: Int): TvMazeEpisode

    // people
    suspend fun personInfo(id: Int): TvMazePerson
    suspend fun personCastCredits(id: Int, embedded: String?): List<TvMazeCastCredit>
    suspend fun personCrewCredits(id: Int): List<TvMazeCrewCredit>

}

// skipped
// lookup
// Schedule by country
// Show alternate lists
// Episodes by date
// Season episodes
// Show AKA's
// Episode guest cast
// Episode guest crew
// Person index