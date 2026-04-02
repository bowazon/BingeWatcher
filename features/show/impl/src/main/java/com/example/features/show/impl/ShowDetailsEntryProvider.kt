package com.example.features.show.impl

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.features.show.api.ShowDetailsNavKey
import com.example.navigation.Navigator

fun EntryProviderScope<NavKey>.showDetailsEntry(navigator: Navigator) {
    entry<ShowDetailsNavKey> { navKey ->
        ShowDetailsScreen(
            onBackClicked = navigator::goBack,
            viewModel = hiltViewModel<ShowDetailsViewModel, ShowDetailsViewModel.Factory>(
                key = "showDetails_${navKey.showId}",
            ) { factory ->
                factory.create(navKey.showId)
            }
        )
    }
}