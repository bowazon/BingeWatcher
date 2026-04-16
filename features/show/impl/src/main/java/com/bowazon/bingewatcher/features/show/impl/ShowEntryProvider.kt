package com.bowazon.bingewatcher.features.show.impl

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.features.show.api.ShowNavKey
import com.bowazon.bingewatcher.core.navigation.Navigator
import com.bowazon.bingewatcher.features.fullcrew.api.navigateToFullCrewShow
import com.bowazon.bingewatcher.features.person.api.navigateToPerson
import com.bowazon.bingewatcher.features.imageview.api.navigateToImageView
import com.bowazon.bingewatcher.features.season.api.navigateToSeason

fun EntryProviderScope<NavKey>.showEntry(navigator: Navigator) {
    entry<ShowNavKey> { navKey ->
        ShowScreen(
            onBackClicked = navigator::goBack,
            navigateToPerson = navigator::navigateToPerson,
            navigateToSeason = navigator::navigateToSeason,
            navigateToImageView = navigator::navigateToImageView,
            navigateToFullCrewShow = navigator::navigateToFullCrewShow,
            viewModel = hiltViewModel<ShowViewModel, ShowViewModel.Factory>(
                key = "show_${navKey.showId}",
            ) { factory ->
                factory.create(navKey.showId)
            }
        )
    }
}