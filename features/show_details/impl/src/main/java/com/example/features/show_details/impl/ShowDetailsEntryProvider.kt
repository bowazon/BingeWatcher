package com.example.features.show_details.impl

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.features.show_details.api.ShowDetailsNavKey
import com.example.navigation.Navigator
import java.util.Map.entry

fun EntryProviderScope<NavKey>.showDetailsEntry(navigator: Navigator) {
    entry<ShowDetailsNavKey> { it ->
        ShowDetailsScreen(
            showDetailed = it.showDetailed,
            onBackClicked = navigator::goBack
        )
    }
}