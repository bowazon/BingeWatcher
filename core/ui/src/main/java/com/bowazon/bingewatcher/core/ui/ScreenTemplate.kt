package com.bowazon.bingewatcher.core.ui

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
fun <T> BingeWatcherDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: ScreenState<T>,
    onBackClicked: () -> Unit,
    largeImageUrl: (T) -> String?,
    header: @Composable (T) -> Unit,
    badge: @Composable ((T) -> Unit)? = null,
    actions: @Composable RowScope.(T) -> Unit,
    content: LazyListScope.(T) -> Unit
) {
    when (uiState) {
        is ScreenState.Loading -> {
            ScreenLoading()
        }

        is ScreenState.Error -> {
            ScreenError(onBackClicked = onBackClicked)
        }

        is ScreenState.Success -> {
            val data = uiState.data
            val lazyListState = rememberLazyListState()

            Box(modifier.fillMaxSize()) {
                LazyColumn(state = lazyListState) {

                    largeImageUrl(data)?.let { url ->
                        item {
                            val painter = rememberAsyncImagePainter(url)
                            BingeWatcherParallaxPicture(
                                lazyListState = lazyListState,
                                picturePainter = painter
                            )
                        }
                    }

                    stickyHeader {
                        header(data)
                    }

                    badge?.let {
                        item { it(data) }
                    }

                    item {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            actions(data)
                        }
                    }

                    content(data)
                }

                BingeWatcherBackIcon(
                    onClick = onBackClicked,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                )
            }
        }
    }
}
