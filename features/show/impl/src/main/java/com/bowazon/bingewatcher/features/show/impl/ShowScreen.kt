package com.bowazon.bingewatcher.features.show.impl

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherSmallPersonCard
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowBottomBadge
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherSmallSeasonCard
import androidx.core.net.toUri
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherBackIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherImdbIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherLikeIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherOfficialSiteIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherParallaxPicture
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShareIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowTopBadge
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherSmallImageCard
import com.bowazon.bingewatcher.core.designsystem.R
import com.bowazon.bingewatcher.core.ui.utils.loadPainter

@Composable
fun ShowScreen(
    modifier: Modifier = Modifier,
    viewModel: ShowViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    navigateToPerson: (Int) -> Unit,
    navigateToSeason: (Int, Int) -> Unit,
    navigateToImageView: (Int, List<String>) -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is ShowScreenEffect.OpenUrl -> {
                    val intent = Intent(Intent.ACTION_VIEW, effect.url.toUri())
                    context.startActivity(intent)
                }
                is ShowScreenEffect.OpenPersonCard -> {
                    navigateToPerson(effect.id)
                }
                is ShowScreenEffect.OpenSeasonCard -> {
                    navigateToSeason(effect.showId, effect.seasonId)
                }
                is ShowScreenEffect.OpenImageView -> {
                    navigateToImageView(effect.imageIndex, effect.urlList)
                }
            }
        }
    }

    when(uiState) {
        is ShowScreenUiState.Loading -> {
            // TODO show loading
        }
        is ShowScreenUiState.Error -> {
            // TODO show error
        }
        is ShowScreenUiState.Success -> {
            ShowScreen(
                modifier = modifier,
                uiData = (uiState as ShowScreenUiState.Success).data,
                onBackClicked = onBackClicked,
                onEvent = viewModel::onEvent,
            )
        }
    }
}

@Composable
internal fun ShowScreen(
    modifier: Modifier = Modifier,
    uiData: ShowUiModel,
    onBackClicked: () -> Unit,
    onEvent: (ShowScreenEvent) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    val painterLargeImage = rememberAsyncImagePainter(model = uiData.show.largeImage)

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            //.background(Color.Black),
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
                    name = uiData.show.summary.name,
                    premiered = uiData.show.summary.premiered,
                    rating = uiData.show.summary.rating
                )
            }

            item {
                BingeWatcherShowBottomBadge(
                    genres = uiData.show.summary.genres,
                    description = uiData.show.description
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BingeWatcherImdbIcon(onClick = { onEvent(ShowScreenEvent.OnImdbClick) })

                    BingeWatcherLikeIcon(onClick = { onEvent(ShowScreenEvent.OnFollowClick) })

                    BingeWatcherOfficialSiteIcon( onClick = { onEvent(ShowScreenEvent.OnOfficialSiteClick) } )

                    BingeWatcherShareIcon(onClick = {
                        // TODO share
                    })
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiData.seasons.size) { i ->
                        val painter = loadPainter(uiData.seasons[i].image, painterLargeImage)

                        BingeWatcherSmallSeasonCard(
                            onClick = { onEvent(ShowScreenEvent.OnSeasonClick(
                                uiData.show.id,
                                uiData.seasons[i].id
                            )) },
                            season = i + 1,
                            episodesAmount = uiData.seasons[i].episodesAmount,
                            premiereDate = uiData.seasons[i].premiereDate,
                            imagePainter = painter
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                if(uiData.cast.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        text = "Cast"
                    )
                }
            }

            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiData.cast) { actor ->
                        val painter = loadPainter(actor.image, painterResource(R.drawable.person_placeholder))

                        BingeWatcherSmallPersonCard(
                            onClick = { onEvent(ShowScreenEvent.OnPersonClick(actor.id)) },
                            personName = actor.name,
                            imagePainter = painter
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                if(uiData.crew.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        text = "Crew"
                    )
                }
            }

            // TODO showing crew this way is pain in the ass. Do we need it here? Should we do separate full crew feature, like in IMDB?
            item {
                val maxLines = uiData.crew.maxOfOrNull { it.role.lines().size } ?: 1
                val lineHeight = 20.dp
                val infoHeight = lineHeight * maxLines

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiData.crew) { crewMember ->
                        val painter = loadPainter(crewMember.image, painterResource(R.drawable.person_placeholder))

                        BingeWatcherSmallPersonCard(
                            onClick = { onEvent(ShowScreenEvent.OnPersonClick(crewMember.id)) },
                            personName = crewMember.name,
                            additionalInfo = crewMember.role,
                            additionalInfoHeight = infoHeight,
                            imagePainter = painter
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                if (uiData.pictures.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        text = "Gallery"
                    )
                }
            }

            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiData.pictures.size) { i ->
                        val painter = loadPainter(uiData.pictures[i], painterLargeImage)

                        BingeWatcherSmallImageCard(
                            painter = painter,
                            onClick = {
                                ShowScreenEvent.OnGalleryClick(i, uiData.pictures)
                            })
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // TODO do we really need it here, white arrow on white surface?
        // TODO return adaptive background?
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
