package com.bowazon.bingewatcher.features.show.impl

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.features.show.api.ShowDetailsNavKey
import com.bowazon.bingewatcher.core.navigation.Navigator
import com.bowazon.bingewatcher.features.person.api.navigateToPerson
import com.bowazon.bingewatcher.features.imageview.api.navigateToImageView
import com.bowazon.bingewatcher.features.season.api.navigateToSeason

fun EntryProviderScope<NavKey>.showDetailsEntry(navigator: Navigator) {
    entry<ShowDetailsNavKey> { navKey ->
        ShowDetailsScreen(
            onBackClicked = navigator::goBack,
            navigateToActor = navigator::navigateToPerson,
            navigateToSeason = navigator::navigateToSeason,
            navigateToImageView = navigator::navigateToImageView,
            viewModel = hiltViewModel<ShowDetailsViewModel, ShowDetailsViewModel.Factory>(
                key = "showDetails_${navKey.showId}",
            ) { factory ->
                factory.create(navKey.showId)
            }
        )
    }
}