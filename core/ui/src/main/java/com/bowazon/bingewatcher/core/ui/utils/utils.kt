package com.bowazon.bingewatcher.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun loadPainter(
    url: String,
    placeholder: Painter
): Painter {
    val localContext = LocalContext.current
    val request = remember(url) {
        ImageRequest.Builder(localContext)
            .data(url)
            .crossfade(500)
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