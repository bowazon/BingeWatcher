package com.example.features.show_details.impl

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.palette.graphics.Palette
//import com.example.designsystem.BingeWatcherShowDetailedCard
import com.example.designsystem.ShowCard
import com.example.entities.ShowDetailed
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.SubcomposeAsyncImage
import coil.size.Size
import com.example.designsystem.BingeWatcherActorSmallCard
import com.example.designsystem.BingeWatcherShowImageWithGradient
import com.example.designsystem.BingeWatcherShowRestDescription
import com.example.designsystem.BingeWatcherShowShortDescription
import com.example.designsystem.BingeWatcherSmallSeasonCard
import com.example.designsystem.R
import com.example.designsystem.utils.getReadableTextColor

const val SHOW_IMAGE_HEIGHT = 600
const val SHOW_IMAGE_HEIGHT_LEAST = 200

@Composable
fun ShowDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: ShowDetailsViewModel,
    showDetailed: ShowDetailed,
    onBackClicked: () -> Unit
) {
    // TODO remember?
    val state by viewModel.state.collectAsState()

    //TODO should be really moved to themes
    var backgroundColor by remember { mutableStateOf(Color.Black) }

    val maxHeight = SHOW_IMAGE_HEIGHT.dp

    val lazyListState = rememberLazyListState()

    val scrollOffset = remember {
        derivedStateOf {
            val index = lazyListState.firstVisibleItemIndex
            val offset = lazyListState.firstVisibleItemScrollOffset

            when {
                index == 0 -> offset
                else -> SHOW_IMAGE_HEIGHT // полностью схлопнули
            }.coerceAtMost(SHOW_IMAGE_HEIGHT)
        }
    }

    val painterLargeImage = rememberAsyncImagePainter(model = showDetailed.largeImage)

    val imageHeight = (maxHeight - with(LocalDensity.current) {
        scrollOffset.value.toDp()
    }).coerceAtLeast(SHOW_IMAGE_HEIGHT_LEAST.dp)

    // TODO do we need scaffold?
    //Scaffold { padding ->
    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundColor),
            state = lazyListState
        ) {
            item {
                BingeWatcherShowImageWithGradient(
                    imageHeight = imageHeight,
                    backgroundColor = backgroundColor,
                    imagePainter = painterLargeImage)
            }

            stickyHeader {//stickyHeader {
                BingeWatcherShowShortDescription(
                    backgroundColor = backgroundColor,
                    name = showDetailed.summary.name,
                    premiered = showDetailed.summary.premiered,
                    rating = showDetailed.summary.rating
                )
            }

            // TODO too much elements in one item
            item {
                BingeWatcherShowRestDescription(
                    backgroundColor = backgroundColor,
                    genres = showDetailed.summary.genres,
                    description = showDetailed.description
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column { //(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        viewModel.redux(ShowDetailsEvent.OnImdbClick)
                    }) {
                        Text("IMDB")
                    }
                    Button(onClick = {
                        viewModel.redux(ShowDetailsEvent.OnOfficialSiteClick)
                    }) {
                        Text("Official site")
                    }
                    Button(onClick = {
                        viewModel.redux(ShowDetailsEvent.OnFollowClick)
                    }) {
                        // TODO should be like if show ended and follow in ongoing
                        Text("Follow")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                state?.let {
                    uiState ->
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.seasons.size) { i ->

                            val painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(uiState.seasons[i].image)
                                    .size(Size.ORIGINAL)
                                    .build()
                            )

                            val painterState = painter.state

                            val currentImagePainter = when (painterState) {
                                is AsyncImagePainter.State.Success -> painterState.painter
                                else -> painterLargeImage
                            }

                            BingeWatcherSmallSeasonCard(
                                season = i + 1,
                                episodesAmount = uiState.seasons[i].episodesAmount,
                                premiereDate = uiState.seasons[i].premiereDate,
                                imagePainter = currentImagePainter,
                                defaultPicture = painterLargeImage
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                state?.let {
                        uiState ->
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.cast.size) { i ->

                            val painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(uiState.cast[i].image)
                                    .size(Size.ORIGINAL)
                                    .build()
                            )

                            val painterState = painter.state

                            val currentImagePainter = when (painterState) {
                                is AsyncImagePainter.State.Success -> painterState.painter
                                else -> painterLargeImage
                            }

                            BingeWatcherActorSmallCard(
                                actorName = uiState.cast[i].name,
                                imagePainter = currentImagePainter,
                                defaultPicture = painterLargeImage,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                state?.let {
                        uiState ->
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.pictures.size) { i ->

                            val painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(uiState.pictures[i])
                                    .size(Size.ORIGINAL)
                                    .build()
                            )

                            val painterState = painter.state

                            val currentImagePainter = when (painterState) {
                                is AsyncImagePainter.State.Success -> painterState.painter
                                else -> painterLargeImage
                            }

                            Image(
                                painter = currentImagePainter,
                                contentDescription = null,
                                contentScale = ContentScale.FillHeight,
                                modifier = Modifier
                                    .height(150.dp)
                            )
                        }
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
                tint = getReadableTextColor(backgroundColor)
            )
        }
    }
}
