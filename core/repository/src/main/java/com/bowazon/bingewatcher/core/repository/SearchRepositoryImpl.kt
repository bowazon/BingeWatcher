package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.Episode
import com.bowazon.bingewatcher.core.entities.Person
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.network.tvmaze.TvMazeRemoteDataSource
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toPerson
import com.bowazon.bingewatcher.core.network.tvmaze.mappers.toShowDetailed
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    val api: TvMazeRemoteDataSource,
) : SearchRepository {
    override suspend fun searchShow(name: String): List<ShowDetailed> {
        // TODO error processing
        return api.search(name).map { it.show.toShowDetailed() }
    }

    override suspend fun searchPeople(name: String): List<Person> {
        return api.peopleSearch(name).map { it.person.toPerson() }
    }
}