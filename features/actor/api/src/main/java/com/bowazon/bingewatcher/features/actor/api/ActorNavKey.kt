package com.bowazon.bingewatcher.features.actor.api

import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.core.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data class ActorNavKey(
    val actorId: Int
) : NavKey

fun Navigator.navigateToActor(
    actorId: Int,
) {
    navigate(ActorNavKey(actorId))
}
