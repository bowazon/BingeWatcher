package com.example.network.tvmaze

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

interface TvMazeRemoteDataSource {
    // search
    suspend fun search(query: String): Result<List<TvMazeShow>>
    suspend fun singleSearch(query: String): Result<TvMazeShowDetails>
    //suspend fun lookup(query: String): Result<TvMazeShowDetails>
    suspend fun peopleSearch(query: String): Result<List<TvMazePersonDetails>>

    // schedule
    suspend fun webSchedule(country: String, date: String): Result<List<TvMazeEpisode>>
    suspend fun fullSchedule(): Result<List<TvMazeEpisode>>

    // shows
    suspend fun showInfo(showId: Int): Result<TvMazeShowDetails>
    suspend fun episodeList(showId: Int): Result<List<TvMazeEpisode>>
    suspend fun episodeByNumber(showId: Int, s: String, e: String): Result<TvMazeEpisode>
    suspend fun episodeByDate(showId: Int, date: String): Result<TvMazeEpisode>
    suspend fun seasons(showId: Int): Result<TvMazeSeason>
    suspend fun cast(showId: Int): Result<List<TvMazeActor>>
    suspend fun crew(showId: Int): Result<List<TvMazeCrewMember>>
    suspend fun images(showId: Int): Result<List<TvMazeShowImage>>
    suspend fun showsPaginated(page: Int): Result<List<TvMazeShowDetails>>

    // Episodes
    suspend fun episode(id: Int): Result<TvMazeEpisode>

    // people
    suspend fun personCastCredits(id: Int): Result<List<TvMazeCastCredit>>
    suspend fun personCrewCredits(id: Int): Result<List<TvMazeCrewCredit>>

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