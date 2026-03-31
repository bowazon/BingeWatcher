package com.example.designsystem

import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import androidx.compose.ui.util.fastCoerceAtLeast
import androidx.core.text.HtmlCompat
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import kotlin.math.tan

const val SHOW_IMAGE_HEIGHT = 600
const val SHOW_IMAGE_HEIGHT_DIMINISHED_ON_SCROLL = 200

@Composable
fun BingeWatcherShowDetailedCard(
    modifier: Modifier = Modifier,
    name: String,
    rating: String,
    genres: String,
    premiered: String,
    imdb: String,
    ended: String,
    officialSite: String,
    description: String,
    bgColor: Color,
    painter: Painter?
) {
    val scrollState = rememberScrollState() // in pixels!

    val maxPx = with(LocalDensity.current) {
        SHOW_IMAGE_HEIGHT.dp.toPx() }
    val minPx = with(LocalDensity.current) {
        SHOW_IMAGE_HEIGHT_DIMINISHED_ON_SCROLL.dp.toPx() }

    val heightPx = (maxPx - scrollState.value).coerceAtLeast(minPx)

    val currentImageHeight = with(LocalDensity.current) { heightPx.toDp() }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = bgColor
    ) {
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Box(modifier = Modifier.fillMaxWidth()
                .height(currentImageHeight)) {

                // Show picture
                Image(
                    painter = painter ?: painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight, // TODO FillHeight or crop? With crop it might look better
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(currentImageHeight) // TODO 600 dp works but must think about original height
                        .align(Alignment.TopCenter)
                )

                // Bottom gradient
                Box(
                    Modifier
                        .matchParentSize()
                        .drawWithCache {
                            val gradient = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, bgColor),
                                startY = size.height * 0.95f,
                                endY = size.height
                            )

                            onDrawWithContent {
                                drawContent()
                                drawRect(gradient)
                            }
                        }
                )

            }



            // Description
            Column(
                modifier = Modifier
                    //.align(Alignment.BottomStart)
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
                val isScrolled by remember {
                    derivedStateOf { scrollState.value > 0 }
                }

                    Column (modifier = Modifier.alpha(if (isScrolled) 1f else 0f)) {
                        HtmlText(
                            html = description,
                            color = Color.White.copy(alpha = 0.9f),
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )

                        // imdb
                        Button(onClick = {}) {
                            Text("IMDB")
                        }

                        // official site
                        Button(onClick = {}) {
                            Text("Official site")
                        }

                        // follow
                        Button(onClick = {}) {
                            Text("Official site")
                        }


                        // block with seasons, cast
                    }

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

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun PreviewBingeWatcherCard() {
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            item(
                selected = true,
                onClick =  {  } ,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                    )
                },
                label = { Text("Discover") }
            )
            item(
                selected = true,
                onClick =  {  } ,
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                    )
                },
                label = { Text("Search") }
            )
        }
    ) {
        BingeWatcherShowDetailedCard(
            name = "Breaking Bad",
            rating = "9.5",
            genres = "Drama, Crime",
            premiered = "2008",
            officialSite = "",
            ended = "",
            imdb = "",
            description = "A high school chemistry teacher turned meth producer...",
            painter = painterResource(R.drawable.girls),
            bgColor = Color.Black
        )
    }

}