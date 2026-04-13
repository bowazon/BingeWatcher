package com.bowazon.bingewatcher.features.season.impl

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.core.navigation.Navigator
import com.bowazon.bingewatcher.features.season.api.SeasonNavKey

fun EntryProviderScope<NavKey>.seasonEntry(navigator: Navigator) {
    entry<SeasonNavKey> { navKey ->
        SeasonScreen(
            onBackClicked = navigator::goBack,
            viewModel = hiltViewModel<SeasonViewModel, SeasonViewModel.Factory>(
                // TODO all these "" things should be part of companion objects of view models or smth
                key = "season_${navKey.seasonId}",
            ) { factory ->
                factory.create(navKey.showId, navKey.seasonId)
            }
        )
    }
}