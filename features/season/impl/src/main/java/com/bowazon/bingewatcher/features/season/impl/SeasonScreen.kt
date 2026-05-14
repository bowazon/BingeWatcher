package com.bowazon.bingewatcher.features.season.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherBackIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherLikeIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowBottomBadge
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherMediumEpisodeCard
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherParallaxPicture
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShareIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowTopBadge
import com.bowazon.bingewatcher.core.designsystem.R
import com.bowazon.bingewatcher.core.designsystem.utils.ToggleState
import com.bowazon.bingewatcher.core.ui.BingeWatcherDetailsScreen
import com.bowazon.bingewatcher.core.ui.ScreenState
import com.bowazon.bingewatcher.core.ui.utils.loadPainter

@Composable
fun SeasonScreen(
    modifier: Modifier = Modifier,
    viewModel: SeasonViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when(effect) {
                is SeasonEffect.OnWatchedLoading -> {
                    // TODO
                }
                is SeasonEffect.OnWatchedLoaded -> {
                    // TODO
                }
            }
        }
    }

    BingeWatcherDetailsScreen(
        modifier = modifier,
        uiState = uiState,
        onBackClicked = onBackClicked,
        largeImageUrl = { it.season.image },
        header = { uiData ->
            BingeWatcherShowTopBadge(
                name = uiData.season.showName + ", season " + uiData.season.seasonNumber,
                premiered = uiData.season.premiereDate,
            )
        },
        badge = { uiData ->
            BingeWatcherShowBottomBadge(
                genres = uiData.season.episodesAmount.toString() +
                        if(uiData.season.episodesAmount == 1) " episode"
                        else " episodes",
                description = ""
            )
        },
        actions = { uiData ->
            BingeWatcherLikeIcon(
                onClick = {
                    viewModel.onEvent(
                        SeasonEvent.OnLikeClicked(
                            uiData.season.id
                        )
                    )
                },
                isLiked = false
            )
            BingeWatcherShareIcon(onClick = {
                // TODO share
            })
        },
    ) { uiData ->
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
                // TODO I have feel this is wrong and doesn't use reactivity
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
}
