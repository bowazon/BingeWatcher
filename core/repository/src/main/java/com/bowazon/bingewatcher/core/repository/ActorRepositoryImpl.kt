package com.bowazon.bingewatcher.core.repository

import android.util.Log
import com.bowazon.bingewatcher.core.entities.ActorCredits
import com.bowazon.bingewatcher.core.entities.ActorDetailed
import com.bowazon.bingewatcher.core.network.tvmaze.TvMazeRemoteDataSource
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toActorCredits
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toActorDetailed
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toShowDetailed
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toShowSummary
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class ActorRepositoryImpl@Inject constructor(
    val api: TvMazeRemoteDataSource
) : ActorRepository {
    override suspend fun getActorInfo(id: Int): ActorDetailed {
        return api.personInfo(id).toActorDetailed()
    }

    override suspend fun getActorCredits(id: Int): List<ActorCredits> {

        val a = api.personCastCredits(id, "show")
        val b = a.map { it.toActorCredits() }

        Log.i("ART_DBG_3", "model is " + a[0].toString())
        Log.i("ART_DBG_3", "entity is " + b[0].toString())

        return b
    }
}