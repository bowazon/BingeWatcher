package com.bowazon.bingewatcher.core.repository

import android.util.Log
import com.bowazon.bingewatcher.core.entities.ActorCredits
import com.bowazon.bingewatcher.core.entities.CrewCredits
import com.bowazon.bingewatcher.core.entities.Person
import com.bowazon.bingewatcher.core.network.tvmaze.EmbedType
import com.bowazon.bingewatcher.core.network.tvmaze.TvMazeRemoteDataSource
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toActorCredits
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toCrewCredits
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toPerson
import javax.inject.Inject

class PersonRepositoryImpl@Inject constructor(
    val api: TvMazeRemoteDataSource
) : PersonRepository {
    override suspend fun getPersonInfo(id: Int): Person {
        return api.personInfo(id).toPerson()
    }

    override suspend fun getActorCredits(id: Int): List<ActorCredits> {

        val a = api.personCastCredits(id, EmbedType.SHOW)
        val b = a.map { it.toActorCredits() }

        if (a.isNotEmpty()) {
            Log.i("ART_DBG_3", "model is ${a[0]}")
        }
        if (b.isNotEmpty()) {
            Log.i("ART_DBG_3", "entity is ${b[0]}")
        }

        return b
    }

    override suspend fun getCrewCredits(id: Int): List<CrewCredits> {
        val a = api.personCrewCredits(id, EmbedType.SHOW)
        val b = a.map { it.toCrewCredits() }

        return b
    }
}