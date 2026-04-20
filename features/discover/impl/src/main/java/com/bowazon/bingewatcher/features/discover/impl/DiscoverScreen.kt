package com.bowazon.bingewatcher.features.discover.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherMediumShowCard
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.ui.utils.loadPainter
import com.bowazon.bingewatcher.core.designsystem.R
import com.bowazon.bingewatcher.core.ui.ScreenState

@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    viewModel: DiscoverViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    when (state.value) {
        is ScreenState.Loading -> {

        }
        is ScreenState.Error -> {

        }
        is ScreenState.Success -> {
            Column {
                LazyRow() {
                    items((state.value as ScreenState.Success<DiscoverUiModel>).data.showsBasedOnLikes) {
                        show ->
                        val painter = loadPainter(show.largeImage)
                        BingeWatcherMediumShowCard(
                            showName = show.summary.name,
                            picturePainter = painter,
                            date = show.summary.premiered,
                            genre = show.summary.genres.joinToString("/"),
                            rating = show.summary.rating,
                            onClick = {}
                        )
                    }
                }
            }
        }
    }


}

