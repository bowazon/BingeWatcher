package com.bowazon.bingewatcher.features.show.api

import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.core.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data class ShowNavKey(
    val showId: Int
) : NavKey

fun Navigator.navigateToShow(
    showId: Int,
) {
    navigate(ShowNavKey(showId))
}
