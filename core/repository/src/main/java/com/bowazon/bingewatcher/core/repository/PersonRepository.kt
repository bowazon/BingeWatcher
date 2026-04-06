package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.ActorCredits
import com.bowazon.bingewatcher.core.entities.CrewCredits
import com.bowazon.bingewatcher.core.entities.Person

interface PersonRepository {
    suspend fun getPersonInfo(id: Int): Person
    suspend fun getActorCredits(id: Int): List<ActorCredits>
    suspend fun getCrewCredits(id: Int): List<CrewCredits>
}