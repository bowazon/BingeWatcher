package com.example.features.search.impl

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.features.search.api.SearchNavKey
import com.example.features.show.api.navigateToShowDetails
import com.example.navigation.Navigator

fun EntryProviderScope<NavKey>.searchEntry(navigator: Navigator) {
    entry<SearchNavKey> {
        SearchScreen(
            onDetailsClicked = navigator::navigateToShowDetails
        )
    }
}
