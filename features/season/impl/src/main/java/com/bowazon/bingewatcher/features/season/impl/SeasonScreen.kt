package com.bowazon.bingewatcher.features.season.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherBackIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherImdbIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherLikeIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowBottomBadge
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherMediumEpisodeCard
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherOfficialSiteIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherParallaxPicture
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShareIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowTopBadge
import com.bowazon.bingewatcher.core.designsystem.utils.CardClickable
import com.bowazon.bingewatcher.core.ui.utils.loadPainter

@Composable
fun SeasonScreen(
    modifier: Modifier = Modifier,
    viewModel: SeasonViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

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

    when(uiState) {
        is SeasonUiState.Loading -> {
            // TODO show loading
        }
        is SeasonUiState.Error -> {
            // TODO show error
        }
        is SeasonUiState.Success -> {
            SeasonScreen(
                modifier = modifier,
                uiData = (uiState as SeasonUiState.Success).data,
                onBackClicked = onBackClicked,
                onEvent = viewModel::onEvent,
            )
        }
    }
}

@Composable
internal fun SeasonScreen(
    modifier: Modifier = Modifier,
    uiData: SeasonUiModel,
    onBackClicked: () -> Unit,
    onEvent: (SeasonEvent) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    val painterLargeImage = rememberAsyncImagePainter(model = uiData.season.image)

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            state = lazyListState
        ) {
            item {
                BingeWatcherParallaxPicture(
                    lazyListState = lazyListState,
                    picturePainter = painterLargeImage
                )
            }

            stickyHeader {
                BingeWatcherShowTopBadge(
                    name = uiData.episodes[0].showName + ", season " + uiData.episodes[0].season,
                    premiered = uiData.season.premiereDate,
                    rating = ""
                )
            }

            item {
                BingeWatcherShowBottomBadge(
                    genres = uiData.season.episodesAmount.toString() +
                            if(uiData.season.episodesAmount == 1) " episode"
                            else " episodes",
                    description = ""
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BingeWatcherLikeIcon(onClick = { onEvent(SeasonEvent.OnLikeClicked(
                        uiData.season.id
                    )) })

                    BingeWatcherShareIcon(onClick = {
                        // TODO share
                    })

                    // TODO mark watched icon
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text ="Episodes:")
            }

            items(uiData.episodes) { episode ->
                val painter = loadPainter(
                    episode.image,
                    painterLargeImage)

                BingeWatcherMediumEpisodeCard(
                    episodeName = episode.name,
                    picturePainter = painter,
                    date = episode.airdate,
                    rating = episode.rating,
                    description = episode.summary,
                    click = CardClickable(
                        flag = episode.watched,
                        isFlagLoading = episode.isUpdatingWatched,
                        onToggle = { onEvent(SeasonEvent.OnWatchedClicked(episode.id)) }
                    )
                )
            }

            // TODO buttons next season previous season
        }


        IconButton(
            onClick = { onBackClicked() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            BingeWatcherBackIcon(onClick = onBackClicked)
        }
    }
}
