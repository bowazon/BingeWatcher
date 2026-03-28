package com.example.features.discover.impl

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.designsystem.BingeWatcherBottomNavigation
import com.example.designsystem.ShowCard
import com.example.designsystem.theme.BingeWatcherTheme
import com.example.entities.ShowDetailed

@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    viewModel: DiscoverViewModel = hiltViewModel()
) {
    val shows by viewModel.searchState.collectAsState()

    Greeting(
        shows = shows
    )
}

@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    shows: List<ShowDetailed>
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            items(shows) { show ->
                ShowCard(
                    name = show.summary.name,
                    pictureUrl = show.summary.image,
                    date = show.summary.premiered,
                    genre = show.summary.genres,
                )
            }
        }
    }
    //FilmReel()
}