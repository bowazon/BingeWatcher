package com.bowazon.bingewatcher.features.fullcrew.api

import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.core.navigation.Navigator
import kotlinx.serialization.Serializable
/*
enum class FullCrewType {
    SHOW,
    EPISODE
}

 */

@Serializable
sealed class FullCrewNavKey : NavKey {

    // TODO so fullcrew feature knows for what type we loading data... is it bad?
    @Serializable
    data class Show(val id: Int) : FullCrewNavKey()

    @Serializable
    data class Episode(val id: Int) : FullCrewNavKey()
}

fun Navigator.navigateToFullCrewShow(id: Int) {
    navigate(FullCrewNavKey.Show(id))
}

fun Navigator.navigateToFullCrewEpisode(id: Int) {
    navigate(FullCrewNavKey.Episode(id))
}