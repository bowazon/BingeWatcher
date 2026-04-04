package com.bowazon.bingewatcher.features.imageview.impl

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch

@Composable
fun ImageViewScreen(
    modifier: Modifier = Modifier,
    imageIndex: Int,
    urlList: List<String>,
    onBackClicked: () -> Unit
) {

    val pagerState = rememberPagerState(
        initialPage = imageIndex,
        pageCount = {
            urlList.size
        })

    var controlsVisible by remember { mutableStateOf(false) }

    val flingBehavior = PagerDefaults.flingBehavior(state = pagerState)

    val coroutineScope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    LaunchedEffect(pagerState.currentPage) {
        listState.animateScrollToItem(
            index = pagerState.currentPage,
            scrollOffset = -((60 / 2))
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    controlsVisible = !controlsVisible
                }
            }
    ) {
        HorizontalPager(
            state = pagerState,
            flingBehavior = flingBehavior,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            ZoomableImage(url = urlList[page])
        }

        if (controlsVisible) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                // top bar with back button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(16.dp)
                ) {
                    IconButton(onClick = { onBackClicked() }) {
                        Icon(
                            Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // bottom bar with gallery and share button
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(16.dp)
                ) {
                    LazyRow(
                        state = listState
                    ) {
                        items(urlList.size) { index ->
                            Image(
                                painter = rememberAsyncImagePainter(urlList[index]),
                                contentDescription = null,
                                alpha = if (index == pagerState.currentPage) 1f else 0.5f,
                                modifier = Modifier
                                    .size(60.dp)
                                    .padding(4.dp)
                                    .clickable {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(index)
                                        }
                                    }
                            )
                        }
                    }

                    IconButton(onClick = {  }) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun ZoomableImage(url: String) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val painter = rememberAsyncImagePainter(url)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                if (scale > 1f) {
                    /*
                    detectTransformGestures { _, pan, zoom, _ ->
                        val newScale = scale * zoom

                        scale = newScale.coerceIn(1f, 5f)

                        if (scale > 1f) {
                            offset += pan
                        } else {
                            offset = Offset.Zero
                        }

                        if (scale <= 1f) {
                            scale = 1f
                            offset = Offset.Zero
                        }
                    }
                    */
                }
            }
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
        )
    }
}
