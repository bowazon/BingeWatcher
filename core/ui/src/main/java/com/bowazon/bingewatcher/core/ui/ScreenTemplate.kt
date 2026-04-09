package com.bowazon.bingewatcher.core.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherBackIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherParallaxPicture

@Composable
private fun ScreenLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Loading") // TODO move to string res
        }
    }
}

@Composable
private fun ScreenError(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Error")

        }
        BingeWatcherBackIcon(
            onClick = onBackClicked,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )
    }
}

@Composable
fun <T>BingeWatcherRootScreen(
    modifier: Modifier = Modifier,
    uiState: ScreenState<T>,
    onBackClicked: () -> Unit,
    content: @Composable (T) -> Unit
) {
    when(uiState) {
        is ScreenState.Loading -> {
            ScreenLoading(modifier)
        }
        is ScreenState.Error -> {
            ScreenError(modifier = modifier, onBackClicked = onBackClicked)
        }
        is ScreenState.Success -> {
            content(uiState.data)
        }
    }
}

@Composable
fun BingeWatcherTemplateScreen(
    modifier: Modifier = Modifier,
    largeImageUrl: String?,
    onBackClicked: () -> Unit,
    stickyHeaderContent: @Composable (() -> Unit)?,
    lowerBadgeContent: @Composable (() -> Unit)?,
    actionRow: @Composable RowScope.() -> Unit,
    contentColumn: LazyListScope.() -> Unit
) {
    val lazyListState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            state = lazyListState
        ) {
            largeImageUrl?.let {
                item {
                    val painterLargeImage = rememberAsyncImagePainter(model = it)
                    BingeWatcherParallaxPicture (
                        lazyListState = lazyListState,
                        picturePainter = painterLargeImage
                    )
                }
            }

            stickyHeader {
                stickyHeaderContent?.let {
                    it()
                }
            }

            item {
                lowerBadgeContent?.let {
                    it()
                }
            }

            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    actionRow()
                }
            }

            contentColumn()
        }

        BingeWatcherBackIcon(
            onClick = onBackClicked,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )
    }
}

