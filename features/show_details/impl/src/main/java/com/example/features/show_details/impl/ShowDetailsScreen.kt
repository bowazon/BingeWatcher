package com.example.features.show_details.impl

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.designsystem.BingeWatcherShowDetailedCard
import com.example.designsystem.ShowCard
import com.example.entities.ShowDetailed
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun ShowDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: ShowDetailsViewModel = hiltViewModel(),
    showDetailed: ShowDetailed
) {
    val context = LocalContext.current

    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var bgColor by remember { mutableStateOf(Color.Black) }
    var isError by remember { mutableStateOf(false) }
    var painter by remember { mutableStateOf<Painter?>(null) }

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
                bgColor = Color(
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



    Scaffold { padding ->
        BingeWatcherShowDetailedCard(
            name = showDetailed.summary.name,
            rating = showDetailed.summary.rating,
            genres = showDetailed.summary.genres,
            premiered = showDetailed.summary.premiered,
            imdb = showDetailed.imdb,
            ended = showDetailed.ended,
            //largeImage = showDetailed.largeImage,
            officialSite = showDetailed.officialSite,
            description = showDetailed.description,
            bgColor = bgColor,
            painter = painter
        )
    }
}