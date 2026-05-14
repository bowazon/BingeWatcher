package com.bowazon.bingewatcher.core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.bowazon.bingewatcher.core.designsystem.R

// We make this separation on purpose - core/designsystem should not know about
// coil, this is the job of core/ui to use coil
@Composable
fun loadPainter(
    url: String,
    placeholder: Painter? = painterResource(R.drawable.show_placeholder)
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

    return when (painter.state) {
        is AsyncImagePainter.State.Success -> painter.state.painter ?: painterResource(R.drawable.show_placeholder)
        else -> placeholder ?: painterResource(R.drawable.show_placeholder)
    }
}
