package com.bowazon.bingewatcher.features.search.impl

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.features.search.api.SearchNavKey
import com.bowazon.bingewatcher.features.show.api.navigateToShow
import com.bowazon.bingewatcher.core.navigation.Navigator
import com.bowazon.bingewatcher.features.person.api.navigateToPerson

fun EntryProviderScope<NavKey>.searchEntry(navigator: Navigator) {
    entry<SearchNavKey> {
        SearchScreen(
            onShowClicked = navigator::navigateToShow,
            onPersonClicked = navigator::navigateToPerson
        )
    }
}
