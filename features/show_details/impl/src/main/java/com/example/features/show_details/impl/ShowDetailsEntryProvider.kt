package com.example.features.show_details.impl

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.entities.ShowDetailed
import com.example.features.show_details.api.ShowDetailsNavKey
import com.example.navigation.Navigator
import java.util.Map.entry

fun EntryProviderScope<NavKey>.showDetailsEntry(navigator: Navigator) {
    entry<ShowDetailsNavKey> { navKey ->
        ShowDetailsScreen(
            showDetailed = navKey.showDetailed,
            onBackClicked = navigator::goBack,
            viewModel = hiltViewModel<ShowDetailsViewModel, ShowDetailsViewModel.Factory>(
                key = "showDetails_${navKey.showDetailed.id}",
            ) { factory ->
                factory.create(navKey.showDetailed)
            }
        )
    }
}