package com.example.designsystem

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlin.math.tan

@Composable
fun BingeWatcherShowDetailedCard(
    modifier: Modifier = Modifier,
    name: String,
    rating: String,
    genres: String,
    premiered: String,
    imdb: String,
    ended: String,
    largeImage: String,
    officialSite: String,
    description: String
) {
    val context = LocalContext.current

    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var bgColor by remember { mutableStateOf(Color.Black) }
    var isError by remember { mutableStateOf(false) }

    // loading image
    LaunchedEffect(largeImage) {
        try {
            val loader = ImageLoader(context)

            val request = ImageRequest.Builder(context)
                .data(largeImage)
                .allowHardware(false)
                .build()

            val result = loader.execute(request)
            val drawable = result.drawable

            if (drawable is BitmapDrawable) {
                val bmp = drawable.bitmap
                bitmap = bmp

                // image background color
                val palette = Palette.from(bmp).generate()
                bgColor = Color(
                    palette.darkMutedSwatch?.rgb
                        ?: palette.dominantSwatch?.rgb
                        ?: Color.Black.toArgb()
                )
            } else {
                isError = true
            }
        } catch (e: Exception) {
            isError = true
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = bgColor
    ) {
        Box(Modifier.fillMaxSize()) {

            // Image itself
            if (bitmap != null && !isError) {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxWidth()
                        //.scale(0.7f)
                        .height(400.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = (20).dp)
                )
            } else if (isError) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                )
            }

            // Bottom gradient
            Box(
                Modifier
                    .fillMaxSize()
                    .drawWithCache {
                        val offset = size.height * tan(
                            Math.toRadians(11.06).toFloat()
                        )

                        val start = Offset(size.width / 2 + offset / 2, 0f)
                        val end = Offset(size.width / 2 - offset / 2, size.height)

                        val gradient = Brush.linearGradient(
                            0.25f to Color.Transparent,
                            1f to Color.Black,
                            start = start,
                            end = end
                        )

                        onDrawWithContent {
                            drawContent()
                            drawRect(gradient)
                        }
                    }
            ) {
                Row(
                    modifier = Modifier
                        .offset(y = (100).dp)
                        .align(Alignment.Center)
                ) {
                    Column(
                        modifier = Modifier
                            //.align(Alignment.Center)
                            .offset(
                                x = (-100).dp,
                                //y = (100).dp
                            )
                    ) {
                        Text(
                            text = name,
                            color = Color.White,
                            style = MaterialTheme.typography.headlineLarge)
                        Text(
                            text = premiered,
                            color = Color.White,
                            style = MaterialTheme.typography.headlineSmall)
                    }
                    Row(
                        modifier = Modifier.offset(x = 100.dp)
                    ) {
                        Text(
                            //modifier = Modifier.offset(x = 100.dp),
                            text = rating,
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium)
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.star),
                            contentDescription = null
                        )
                    }

                }
            }
        }
    }
}
