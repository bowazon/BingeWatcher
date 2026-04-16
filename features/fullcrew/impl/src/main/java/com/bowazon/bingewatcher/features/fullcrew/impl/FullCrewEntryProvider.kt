package com.bowazon.bingewatcher.features.fullcrew.impl

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.core.navigation.Navigator
import com.bowazon.bingewatcher.core.repository.FullCrewRequest
import com.bowazon.bingewatcher.features.fullcrew.api.FullCrewNavKey
import com.bowazon.bingewatcher.features.person.api.navigateToPerson

fun EntryProviderScope<NavKey>.fullCrewEntry(navigator: Navigator) {
    entry<FullCrewNavKey.Show> { navKey ->
        val request = FullCrewRequest.Show(navKey.id)

        FullCrewScreen(
            onBackClicked = navigator::goBack,
            navigateToPerson = navigator::navigateToPerson,
            viewModel = hiltViewModel<FullCrewViewModel, FullCrewViewModel.Factory>(
                // TODO all these "" things should be part of companion objects of view models or smth
                key = "FullCrew_${request}",
            ) { factory ->
                factory.create(request)
            }
        )
    }
    entry<FullCrewNavKey.Episode> { navKey ->
        val request = FullCrewRequest.Episode(navKey.id)

        FullCrewScreen(
            onBackClicked = navigator::goBack,
            navigateToPerson = navigator::navigateToPerson,
            viewModel = hiltViewModel<FullCrewViewModel, FullCrewViewModel.Factory>(
                // TODO all these "" things should be part of companion objects of view models or smth
                key = "FullCrew_${request}",
            ) { factory ->
                factory.create(request)
            }
        )
    }
}