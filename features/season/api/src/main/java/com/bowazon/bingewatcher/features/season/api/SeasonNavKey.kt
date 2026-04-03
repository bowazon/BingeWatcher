package com.bowazon.bingewatcher.features.season.api

import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.core.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data class SeasonNavKey(
    val showId: Int,
    val seasonId: Int
) : NavKey

fun Navigator.navigateToSeason(
    showId: Int,
    seasonId: Int
) {
    navigate(SeasonNavKey(showId, seasonId))
}
