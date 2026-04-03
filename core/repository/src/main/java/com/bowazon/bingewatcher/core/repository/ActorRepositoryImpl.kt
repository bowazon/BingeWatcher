package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.ActorDetailed
import com.bowazon.bingewatcher.core.network.tvmaze.TvMazeRemoteDataSource
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toActorDetailed
import javax.inject.Inject

class ActorRepositoryImpl@Inject constructor(
    val api: TvMazeRemoteDataSource
) : ActorRepository {
    override suspend fun getActorInfo(id: Int): ActorDetailed {
        return api.personInfo(id).toActorDetailed()
    }

    override suspend fun getActorCredits(id: Int): List<String> {
        TODO("Not yet implemented")
    }
}