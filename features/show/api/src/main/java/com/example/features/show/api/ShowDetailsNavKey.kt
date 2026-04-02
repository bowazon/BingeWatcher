package com.example.features.show.api

import androidx.navigation3.runtime.NavKey
import com.example.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data class ShowDetailsNavKey(
    val showId: Int
) : NavKey

fun Navigator.navigateToShowDetails(
    showId: Int,
) {
    navigate(ShowDetailsNavKey(showId))
}
