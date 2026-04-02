package com.bowazon.bingewatcher.features.discover.impl

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.features.discover.api.DiscoverNavKey
import com.bowazon.bingewatcher.core.navigation.Navigator

fun EntryProviderScope<NavKey>.discoverEntry(navigator: Navigator) {
    entry<DiscoverNavKey> {
        DiscoverScreen()
    }
}
