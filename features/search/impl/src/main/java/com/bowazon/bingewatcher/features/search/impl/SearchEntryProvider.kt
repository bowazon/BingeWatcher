package com.bowazon.bingewatcher.features.search.impl

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.features.search.api.SearchNavKey
import com.bowazon.bingewatcher.features.show.api.navigateToShowDetails
import com.bowazon.bingewatcher.core.navigation.Navigator

fun EntryProviderScope<NavKey>.searchEntry(navigator: Navigator) {
    entry<SearchNavKey> {
        SearchScreen(
            onDetailsClicked = navigator::navigateToShowDetails
        )
    }
}
