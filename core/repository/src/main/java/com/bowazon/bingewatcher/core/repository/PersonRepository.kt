package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.ActorCredits
import com.bowazon.bingewatcher.core.entities.ActorDetailed

interface PersonRepository {
    suspend fun getActorInfo(id: Int): ActorDetailed
    suspend fun getActorCredits(id: Int): List<ActorCredits>
    //suspend fun getCrewCredits(id: Int): List<CrewCredits>
}