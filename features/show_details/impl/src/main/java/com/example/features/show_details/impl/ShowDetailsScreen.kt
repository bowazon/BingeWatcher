package com.example.features.show_details.impl

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
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
import com.example.designsystem.BingeWatcherShowImageWithGradient
import com.example.designsystem.BingeWatcherShowRestDescription
import com.example.designsystem.BingeWatcherShowShortDescription
import com.example.designsystem.R
import com.example.designsystem.utils.getReadableTextColor

const val SHOW_IMAGE_HEIGHT = 600

@Composable
fun ShowDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: ShowDetailsViewModel = hiltViewModel(),
    showDetailed: ShowDetailed,
    onBackClicked: () -> Unit
) {
    val context = LocalContext.current

    // TODO remember?
    val state by viewModel.state.collectAsState()

    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var backgroundColor by remember { mutableStateOf(Color.Black) }
    var isError by remember { mutableStateOf(false) }
    var painter by remember { mutableStateOf<Painter?>(null) }

    val maxHeight = SHOW_IMAGE_HEIGHT.dp

    val lazyListState = rememberLazyListState()

    val scrollOffset = remember {
        derivedStateOf {
            lazyListState.firstVisibleItemScrollOffset.coerceAtMost(SHOW_IMAGE_HEIGHT)
        }
    }

    val imageHeight = maxHeight - with(LocalDensity.current){ scrollOffset.value.toDp() }


    LaunchedEffect(showDetailed.largeImage) {
        try {
            val loader = ImageLoader(context)

            val request = ImageRequest.Builder(context)
                .data(showDetailed.largeImage)
                .allowHardware(false)
                .build()

            val result = loader.execute(request)
            val drawable = result.drawable

            if (drawable is BitmapDrawable) {
                val bmp = drawable.bitmap
                bitmap = bmp
                painter = BitmapPainter(bitmap!!.asImageBitmap())

                val palette = Palette.from(bmp).generate()
                backgroundColor = Color(
                    palette.darkMutedSwatch?.rgb
                        ?: palette.dominantSwatch?.rgb
                        ?: Color.Black.toArgb()
                )
            } else {
                //isError = true
                painter = null
            }
        } catch (e: Exception) { // TODO which exception
            //isError = true
            painter = null
        }
    }

    var actorImages by remember { mutableStateOf<MutableList<BitmapPainter>>(mutableListOf<BitmapPainter>()) }
/*
    state?.let {
        LaunchedEffect(state?.cast) {
            // TODO, should we load images here or should it do ViewModel or Repo?
            val loader = ImageLoader(context)

            for (c in state!!.cast) {
                val request = ImageRequest.Builder(context)
                    .data(c.image)
                    .allowHardware(false)
                    .build()

                val result = loader.execute(request)
                val drawable = (result.drawable as BitmapDrawable).bitmap

                drawable?.let {
                    actorImages.add(BitmapPainter(drawable.asImageBitmap()))
                }
            }
        }
    }
*/

    // TODO do we need scaffold?
    //Scaffold { padding ->
    Box(modifier = Modifier.fillMaxSize()) {
        val maxHeight = SHOW_IMAGE_HEIGHT.dp

        val lazyListState = rememberLazyListState()

        val scrollOffset = remember {
            derivedStateOf {
                lazyListState.firstVisibleItemScrollOffset.coerceAtMost(SHOW_IMAGE_HEIGHT)
            }
        }

        val imageHeight = maxHeight - with(LocalDensity.current){ scrollOffset.value.toDp() }

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
                    painter = painter)
            }

            stickyHeader {
                BingeWatcherShowShortDescription(
                    backgroundColor = backgroundColor,
                    name = showDetailed.summary.name,
                    premiered = showDetailed.summary.premiered,
                    rating = showDetailed.summary.rating
                )
            }

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
                        Text("Follow")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // others: cast, seasons, etc
                /*
                Column() {
                    LazyRow() {
                        items(actorImages.size) { // TODO !!
                            it ->
                            Image(
                                painter = actorImages[it],
                                contentDescription = null
                            )
                        }
                    }
                }*/
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

