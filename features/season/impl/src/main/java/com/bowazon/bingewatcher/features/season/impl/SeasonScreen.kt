package com.bowazon.bingewatcher.features.season.impl

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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.size.Size
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
fun SeasonScreen(
    modifier: Modifier = Modifier,
    viewModel: SeasonViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->

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
    val firstElementScrollOffset = rememberFirstElementScrollOffset(lazyListState)
    val painterLargeImage = rememberAsyncImagePainter(model = uiData.season.image)

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
                    name = "season123456",
                    premiered = "test 1",
                    rating = "test 2"
                )
            }

            item {
                BingeWatcherShowRestDescription(
                    genres = "test 3",
                    description = "test 4"
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Button(onClick = {
                        //onEvent(ShowDetailsEvent.OnImdbClick)
                    }) {
                        Text("IMDB")
                    }
                    Button(onClick = {
                        //onEvent(ShowDetailsEvent.OnOfficialSiteClick)
                    }) {
                        Text("Official site")
                    }
                    Button(onClick = {
                        //onEvent(ShowDetailsEvent.OnFollowClick)
                    }) {
                        // TODO should be like if show ended and follow in ongoing
                        Text("Follow")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

        }


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
