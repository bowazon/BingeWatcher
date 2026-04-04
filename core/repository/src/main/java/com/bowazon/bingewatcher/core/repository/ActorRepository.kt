package com.bowazon.bingewatcher.core.repository

import com.bowazon.bingewatcher.core.entities.ActorCredits
import com.bowazon.bingewatcher.core.entities.ActorDetailed

interface ActorRepository {
    suspend fun getActorInfo(id: Int): ActorDetailed
    suspend fun getActorCredits(id: Int): List<ActorCredits>
}