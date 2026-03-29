package com.example.designsystem

import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlin.math.tan

@Preview
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
        Box(modifier = Modifier.fillMaxSize()) {

            // Show picture
            when {
                bitmap != null && !isError -> {
                    Image(
                        bitmap = bitmap!!.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop, // TODO FillHeight or crop? With crop it might look better
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(600.dp)
                            .align(Alignment.TopCenter)
                    )
                }

                isError -> {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .align(Alignment.TopCenter)
                    )
                }
            }

            // Bottom gradient
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .drawWithCache {
                        val gradient = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                bgColor//Color.Black
                            ),
                            startY = size.height * 0.3f,
                            endY = size.height
                        )

                        onDrawWithContent {
                            drawContent()
                            drawRect(gradient)
                        }
                    }
            )

            // Description
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                // Name and rating
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = name,
                            color = Color.White,
                            style = MaterialTheme.typography.headlineLarge,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = premiered,
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (rating != "") {
                            Text(
                                text = rating,
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Image(
                                painter = painterResource(R.drawable.star),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Genres
                Text(
                    text = genres,
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Description
                HtmlText(
                    html = description,
                    color = Color.White.copy(alpha = 0.9f),
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun HtmlText(
    html: String,
    color: Color,
    style: TextStyle,
    maxLines: Int,
    overflow: TextOverflow
) {
    val spanned = remember(html) {
        HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

    Text(
        text = spanned.toAnnotatedString(),
        color = color,
        style = style,
        maxLines = maxLines,
        overflow = overflow
    )
}

fun Spanned.toAnnotatedString(): AnnotatedString {
    return buildAnnotatedString {
        append(this@toAnnotatedString.toString())

        getSpans(0, length, StyleSpan::class.java).forEach { span ->
            val start = getSpanStart(span)
            val end = getSpanEnd(span)

            when (span.style) {
                Typeface.BOLD -> addStyle(
                    SpanStyle(fontWeight = FontWeight.Bold),
                    start,
                    end
                )
                Typeface.ITALIC -> addStyle(
                    SpanStyle(fontStyle = FontStyle.Italic),
                    start,
                    end
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBingeWatcherCard() {
    BingeWatcherShowDetailedCard(
        name = "Breaking Bad",
        rating = "9.5",
        genres = "Drama, Crime",
        premiered = "2008",
        description = "A high school chemistry teacher turned meth producer...",
        image = painterResource(R.drawable.ic_launcher_foreground),
        bgColor = Color.DarkGray
    )
}