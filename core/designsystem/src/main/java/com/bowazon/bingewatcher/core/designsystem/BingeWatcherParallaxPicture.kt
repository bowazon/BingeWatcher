package com.bowazon.bingewatcher.core.designsystem

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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

    val alpha = (1f - (firstElementScrollOffset / SHOW_IMAGE_HEIGHT)).coerceIn(0.7f, 1f)

    val imageHeight = (SHOW_IMAGE_HEIGHT - firstElementScrollOffset)
        .coerceAtLeast(SHOW_IMAGE_HEIGHT_LEAST)

    BingeWatcherShowImageWithGradient(
        modifier = modifier.graphicsLayer {
            this.alpha = alpha
        },
        imageHeight = imageHeight,
        imagePainter = picturePainter)
}