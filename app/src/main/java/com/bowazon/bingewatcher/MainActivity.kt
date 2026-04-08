package com.bowazon.bingewatcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import com.bowazon.bingewatcher.core.designsystem.theme.BingeWatcherTheme
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherBottomNavigation
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation3.ui.NavDisplay
import com.bowazon.bingewatcher.features.discover.api.DiscoverNavKey
import com.bowazon.bingewatcher.features.discover.impl.discoverEntry
import com.bowazon.bingewatcher.features.search.api.SearchNavKey
import com.bowazon.bingewatcher.features.search.impl.searchEntry
import com.bowazon.bingewatcher.features.show.impl.showDetailsEntry
import com.bowazon.bingewatcher.core.navigation.Navigator
import com.bowazon.bingewatcher.core.navigation.toEntries
import com.bowazon.bingewatcher.features.person.impl.personEntry
import com.bowazon.bingewatcher.features.imageview.impl.imageViewEntry
import com.bowazon.bingewatcher.features.season.impl.seasonEntry

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BingeWatcherTheme {
                val navigator = Navigator(
                    NavBackStack<NavKey>(SnapshotStateList<NavKey>().apply {
                        add(DiscoverNavKey)
                        add(SearchNavKey)
                    })
                )
                BingeWatcherBottomNavigation(
                    navigator = navigator,
                    discoverKey = DiscoverNavKey,
                    searchKey = SearchNavKey,
                    myLibraryKey = SearchNavKey
                ) {
                    //Scaffold(modifier = Modifier.fillMaxSize()) {
                    val entryProvider = entryProvider {
                        discoverEntry(navigator)
                        searchEntry(navigator)
                        showDetailsEntry(navigator)
                        personEntry(navigator)
                        seasonEntry(navigator)
                        imageViewEntry(navigator)
                    }
                    NavDisplay(
                        entries = navigator.toEntries(entryProvider),
                        //sceneStrategy = listDetailStrategy,
                        onBack = { navigator.goBack() },
                    )
                    //}
                }
            }
        }
    }
}

