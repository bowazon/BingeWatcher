package com.bowazon.bingewatcher.features.show.impl

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.size.Size
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherActorSmallCard
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowImageWithGradient
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowRestDescription
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowShortDescription
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherSmallSeasonCard
import com.bowazon.bingewatcher.core.designsystem.utils.getReadableTextColor
import androidx.core.net.toUri

private val SHOW_IMAGE_HEIGHT = 600.dp
private val SHOW_IMAGE_HEIGHT_LEAST = 200.dp

@Composable
private fun rememberFirstElementScrollOffset(
    lazyListState: LazyListState
): Dp {
    val density = LocalDensity.current

    return remember(lazyListState, density) {
        derivedStateOf {
            if (lazyListState.firstVisibleItemIndex == 0) {
                with(density) {
                    lazyListState.firstVisibleItemScrollOffset.toDp()
                }
            } else SHOW_IMAGE_HEIGHT
        }
    }.value
}

@Composable
private fun loadPainter(
    url: String,
    placeholder: Painter
): Painter {
    val localContext = LocalContext.current
    val request = remember(url) {
        ImageRequest.Builder(localContext)
            .data(url)
            .size(Size.ORIGINAL)
            .build()
    }
    val painter = rememberAsyncImagePainter(
        model = request
    )

    val painterState = painter.state

    return when (painterState) {
        is AsyncImagePainter.State.Success -> painterState.painter
        else -> placeholder
    }
}

@Composable
fun ShowDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: ShowDetailsViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    navigateToActor: (Int) -> Unit,
    navigateToSeason: (Int, Int) -> Unit,
    navigateToImageView: (Int, List<String>) -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is ShowDetailsEffect.OpenUrl -> {
                    val intent = Intent(Intent.ACTION_VIEW, effect.url.toUri())
                    context.startActivity(intent)
                }
                is ShowDetailsEffect.OpenActorCard -> {
                    navigateToActor(effect.id)
                }
                is ShowDetailsEffect.OpenSeasonCard -> {
                    navigateToSeason(effect.showId, effect.seasonId)
                }
                is ShowDetailsEffect.OpenImageView -> {
                    navigateToImageView(effect.imageIndex, effect.urlList)
                }
            }
        }
    }

    when(uiState) {
        is ShowDetailsUiState.Loading -> {
            // TODO show loading
        }
        is ShowDetailsUiState.Error -> {
            // TODO show error
        }
        is ShowDetailsUiState.Success -> {
            ShowDetailsScreen(
                modifier = modifier,
                uiData = (uiState as ShowDetailsUiState.Success).data,
                onBackClicked = onBackClicked,
                onEvent = viewModel::onEvent,
            )
        }
    }
}

@Composable
internal fun ShowDetailsScreen(
    modifier: Modifier = Modifier,
    uiData: ShowDetailsUiModel,
    onBackClicked: () -> Unit,
    onEvent: (ShowDetailsEvent) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val firstElementScrollOffset = rememberFirstElementScrollOffset(lazyListState)
    val painterLargeImage = rememberAsyncImagePainter(model = uiData.showDetails.largeImage)

    val alpha = (1f - (firstElementScrollOffset / SHOW_IMAGE_HEIGHT)).coerceIn(0.7f, 1f)

    val imageHeight = (SHOW_IMAGE_HEIGHT - firstElementScrollOffset)
        .coerceAtLeast(SHOW_IMAGE_HEIGHT_LEAST)

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
                //.background(Color.Black),
            state = lazyListState
        ) {
            item {
                BingeWatcherShowImageWithGradient(
                    modifier = Modifier.graphicsLayer {
                        this.alpha = alpha
                    },
                    imageHeight = imageHeight,
                    imagePainter = painterLargeImage)
            }

            stickyHeader {
                BingeWatcherShowShortDescription(
                    name = uiData.showDetails.summary.name,
                    premiered = uiData.showDetails.summary.premiered,
                    rating = uiData.showDetails.summary.rating
                )
            }

            item {
                BingeWatcherShowRestDescription(
                    genres = uiData.showDetails.summary.genres,
                    description = uiData.showDetails.description
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Button(onClick = {
                        onEvent(ShowDetailsEvent.OnImdbClick)
                    }) {
                        Text("IMDB")
                    }
                    Button(onClick = {
                        onEvent(ShowDetailsEvent.OnOfficialSiteClick)
                    }) {
                        Text("Official site")
                    }
                    Button(onClick = {
                        onEvent(ShowDetailsEvent.OnFollowClick)
                    }) {
                        // TODO should be like if show ended and follow in ongoing
                        Text("Follow")
                    }
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
                            modifier = Modifier.clickable{
                                onEvent(ShowDetailsEvent.OnSeasonClick(
                                    uiData.showDetails.id,
                                    uiData.seasons[i].id
                                ))
                            },
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
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiData.cast) { actor ->
                        val painter = loadPainter(actor.image, painterLargeImage)

                        BingeWatcherActorSmallCard(
                            modifier.clickable {
                                onEvent(ShowDetailsEvent.OnActorClick(actor.id))
                            },
                            actorName = actor.name,
                            imagePainter = painter
                        )
                    }
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
                    items(uiData.pictures.size) { i ->

                        val painter = loadPainter(uiData.pictures[i], painterLargeImage)

                        Image(
                            painter = painter,
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                                .height(150.dp)
                                .clickable {
                                    onEvent(ShowDetailsEvent.OnGalleryClick(
                                        i, uiData.pictures)) // TODO is it safe to call uiData.pictures? Can't it change concurrently?
                                }
                        )
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
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = null,
                tint = getReadableTextColor(Color.Black)
            )
        }
    }
}
