package com.bowazon.bingewatcher.features.discover.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowCard
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.ui.utils.loadPainter
import com.bowazon.bingewatcher.core.designsystem.R

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
                val painter = loadPainter(
                    show.summary.image,
                    painterResource(R.drawable.show_placeholder)
                )
                BingeWatcherShowCard(
                    showName = show.summary.name,
                    picturePainter = painter,
                    date = show.summary.premiered,
                    genre = show.summary.genres,
                    rating = show.summary.rating
                )
            }
        }
    }
    //FilmReel()
}