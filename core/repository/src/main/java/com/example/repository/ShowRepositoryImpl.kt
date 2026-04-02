package com.example.repository

import com.example.entities.Actor
import com.example.entities.Season
import com.example.entities.ShowDetailed
import com.example.network.tvmaze.TvMazeRemoteDataSource
import com.example.network.tvmaze.mappers.toActor
import com.example.network.tvmaze.mappers.toImageUrl
import com.example.network.tvmaze.mappers.toSeason
import com.example.network.tvmaze.mappers.toShowDetailed
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    val api: TvMazeRemoteDataSource
) : ShowRepository {

    override suspend fun getShow(showId: Int): ShowDetailed {
        return api.showInfo(showId).toShowDetailed()
    }

    override suspend fun getActorsForShow(showId: Int): List<Actor> {
        return api.cast(showId).map { it.toActor() }
    }

    override suspend fun getSeasonsForShow(showId: Int): List<Season> {
        return api.seasons(showId).map { it.toSeason() }
    }

    override suspend fun getImagesForShow(showId: Int): List<String> {
        return api.images(showId).mapNotNull { it.toImageUrl() }
    }
}