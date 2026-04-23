package com.bowazon.bingewatcher.features.discover.impl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherMediumScheduledEpisodeCard
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherSmallShowCard
import com.bowazon.bingewatcher.core.ui.utils.loadPainter

@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier,
    viewModel: DiscoverViewModel = hiltViewModel(),
    navigateToShow: (Int) -> Unit
) {
    val showsBasedOnLikes by viewModel.shows.collectAsStateWithLifecycle()
    val episodesThisWeek by viewModel.episodes.collectAsStateWithLifecycle()
    val isLoadingMoreEpisodes by viewModel.isLoadingMoreEpisodes.collectAsStateWithLifecycle()
    val isLoadedShows by viewModel.isLoadedShows.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .collect { layoutInfo ->
                val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index
                val total = layoutInfo.totalItemsCount

                if (lastVisible != null && lastVisible >= total - 3) {
                    viewModel.loadNextDay()
                }
            }
    }

    Scaffold { paddingValues ->
        val groupedEpisodes = episodesThisWeek.groupBy {
            it.episode.airdate
        }
        LazyColumn(state = listState, modifier = Modifier.padding(paddingValues)) {
            item {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = "For you",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(12.dp))
            }
            item {
                if (isLoadedShows) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(showsBasedOnLikes) { show ->
                            val painter = loadPainter(show.largeImage)
                            BingeWatcherSmallShowCard(
                                name = show.summary.name,
                                imagePainter = painter,
                                date = show.summary.premiered,
                                rating = show.summary.rating,
                                onClick = { navigateToShow(show.id) }
                            )
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            item {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = "Airing this week",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(12.dp))
            }

            groupedEpisodes.forEach { (date, episodes) ->

                stickyHeader {
                    Text(
                        text = date,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }

                items(episodes) { episode ->
                    val showPainter = loadPainter(episode.showImage)
                    val painter = loadPainter(episode.episode.image, showPainter)

                    BingeWatcherMediumScheduledEpisodeCard(
                        showName = episode.episode.showName,
                        episode = episode.episode.episode.toString(),
                        season = episode.episode.season.toString(),
                        genre = episode.showGenres.joinToString("/"),
                        picturePainter = painter,
                        date = episode.episode.airdate,
                        showRating = episode.showRating,
                        onClick = { navigateToShow(episode.showId) }
                    )
                }

                if (isLoadingMoreEpisodes) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }

}

