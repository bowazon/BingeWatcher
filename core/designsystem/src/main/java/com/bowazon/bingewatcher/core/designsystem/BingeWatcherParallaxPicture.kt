package com.bowazon.bingewatcher.core.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bowazon.bingewatcher.core.designsystem.theme.BingeWatcherTheme
import com.bowazon.bingewatcher.core.designsystem.theme.gradientDarkColor

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
fun BingeWatcherParallaxPicture(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    picturePainter: Painter
) {
    val firstElementScrollOffset = rememberFirstElementScrollOffset(lazyListState)

    val alpha = (1f - (firstElementScrollOffset / SHOW_IMAGE_HEIGHT)).coerceIn(0.5f, 1f)

    val imageHeight = (SHOW_IMAGE_HEIGHT - firstElementScrollOffset)
        .coerceAtLeast(SHOW_IMAGE_HEIGHT_LEAST)

    LargeImageWithGradient(
        modifier = modifier.graphicsLayer {
            this.alpha = alpha
        },
        imageHeight = imageHeight,
        imagePainter = picturePainter)
}


@Composable
private fun LargeImageWithGradient(
    modifier: Modifier = Modifier,
    imageHeight: Dp,
    imagePainter: Painter
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(imageHeight)
    ) {
        Surface {
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight
            )
        }

        Box(modifier = Modifier
            .matchParentSize()
            .drawWithCache {
                val gradient = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, gradientDarkColor),
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
}

@Preview(
    widthDp = 412,
    heightDp = 915,
)
@Composable
fun BingeWatcherLargeImageWithGradientPreview() {
    BingeWatcherTheme(
        darkTheme = true,
        dynamicColor = false,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides Color.White
        ) {
            val lazyListState = LazyListState(
                firstVisibleItemIndex = 0,
                firstVisibleItemScrollOffset = 200 // TODO no scroll
            )
            BingeWatcherParallaxPicture(
                lazyListState = lazyListState,
                picturePainter = painterResource(R.drawable.breaking_bad_official)
            )
        }
    }
}