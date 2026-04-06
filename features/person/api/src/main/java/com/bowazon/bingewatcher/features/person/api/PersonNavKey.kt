package com.bowazon.bingewatcher.features.person.api

import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.core.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data class PersonNavKey(
    val personId: Int
) : NavKey

fun Navigator.navigateToPerson(
    personId: Int,
) {
    navigate(PersonNavKey(personId))
}
