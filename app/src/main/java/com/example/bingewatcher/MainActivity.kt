package com.example.bingewatcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import com.example.designsystem.ShowCard
import com.example.designsystem.theme.BingeWatcherTheme
import com.example.designsystem.BingeWatcherBottomNavigation
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation3.ui.NavDisplay
import com.example.features.discover.api.DiscoverNavKey
import com.example.features.discover.impl.discoverEntry
import com.example.features.search.api.SearchNavKey
import com.example.features.search.impl.searchEntry
import com.example.navigation.Navigator
import com.example.navigation.toEntries

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

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
                    navigator,
                    DiscoverNavKey,
                    SearchNavKey
                ) {
                    //Scaffold(modifier = Modifier.fillMaxSize()) {
                    val entryProvider = entryProvider {
                        discoverEntry(navigator)
                        searchEntry(navigator)
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

