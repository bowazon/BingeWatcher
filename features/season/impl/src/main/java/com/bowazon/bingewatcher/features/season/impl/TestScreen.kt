package com.bowazon.bingewatcher.features.season.impl

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherLikeIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherMediumEpisodeCard
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShareIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowBottomBadge
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowTopBadge
import com.bowazon.bingewatcher.core.designsystem.utils.ToggleState
import com.bowazon.bingewatcher.core.ui.utils.loadPainter
import com.bowazon.bingewatcher.core.designsystem.R
import com.bowazon.bingewatcher.core.ui.BingeWatcherRootScreen
import com.bowazon.bingewatcher.core.ui.BingeWatcherTemplateScreen

@Composable
fun TestScreen(
    modifier: Modifier = Modifier,
    viewModel: SeasonViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    /*
    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when(effect) {
                is SeasonEffect.OnWatchedLoading -> {

                }
                is SeasonEffect.OnWatchedLoaded -> {

                }
            }
        }
    }
     */


    BingeWatcherRootScreen(
        uiState = uiState,
        onBackClicked = onBackClicked
    ) { uiData ->
        BingeWatcherTemplateScreen(
            modifier = modifier,
            largeImageUrl = uiData.season.image,
            onBackClicked = onBackClicked,
            stickyHeaderContent = {
                    BingeWatcherShowTopBadge(
                        name = uiData.season.showName + ", season " + uiData.season.seasonNumber,
                        premiered = uiData.season.premiereDate,
                        rating = ""
                    )
                },
            lowerBadgeContent = {
                BingeWatcherShowBottomBadge(
                    genres = uiData.season.episodesAmount.toString() +
                            if(uiData.season.episodesAmount == 1) " episode"
                            else " episodes",
                    description = ""
                )
            },
            actionRow = {
                BingeWatcherLikeIcon(onClick = {
                    viewModel.onEvent(
                        SeasonEvent.OnLikeClicked(
                            uiData.season.id
                        )
                    )
                })
                BingeWatcherShareIcon(onClick = {
                    // TODO share
                })
            },
            contentColumn = {
                item {
                    Text(
                        "Episodes:",
                        modifier = Modifier.padding(16.dp)
                    )
                }

                items(uiData.episodes) { episode ->

                    val painter = loadPainter(
                        episode.image,
                        painterResource(R.drawable.show_placeholder)
                    )

                    BingeWatcherMediumEpisodeCard(
                        episodeName = episode.name,
                        picturePainter = painter,
                        date = episode.airdate,
                        rating = episode.rating,
                        description = episode.summary,
                        toggleState = ToggleState(
                            isChecked = episode.watched,
                            isLoading = episode.isUpdatingWatched
                        ),
                        onToggle = {
                            viewModel.onEvent(
                                SeasonEvent.OnWatchedClicked(episode.id)
                            )
                        }
                    )
                }
            }
        )
    }
}

