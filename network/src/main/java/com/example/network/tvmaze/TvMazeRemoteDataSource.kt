package com.example.network.tvmaze

import com.example.network.tvmaze.models.TvMazeActor
import com.example.network.tvmaze.models.TvMazeCastCredit
import com.example.network.tvmaze.models.TvMazeCrewCredit
import com.example.network.tvmaze.models.TvMazeCrewMember
import com.example.network.tvmaze.models.TvMazeEpisode
import com.example.network.tvmaze.models.TvMazeScoredPerson
import com.example.network.tvmaze.models.TvMazeScoredShow
import com.example.network.tvmaze.models.TvMazeSeason
import com.example.network.tvmaze.models.TvMazeShow
import com.example.network.tvmaze.models.TvMazeShowImage

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
    suspend fun seasons(showId: Int): TvMazeSeason
    suspend fun cast(showId: Int): List<TvMazeActor>
    suspend fun crew(showId: Int): List<TvMazeCrewMember>
    suspend fun images(showId: Int): List<TvMazeShowImage>
    suspend fun showsPaginated(page: Int): List<TvMazeShow>

    // Episodes
    suspend fun episode(id: Int): TvMazeEpisode

    // people
    suspend fun personCastCredits(id: Int): List<TvMazeCastCredit>
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