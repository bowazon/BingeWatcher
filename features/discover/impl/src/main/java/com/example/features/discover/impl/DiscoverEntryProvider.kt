package com.example.features.discover.impl

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.features.discover.api.DiscoverNavKey
import com.example.navigation.Navigator

fun EntryProviderScope<NavKey>.discoverEntry(navigator: Navigator) {
    entry<DiscoverNavKey> {
        DiscoverScreen()
    }
}
