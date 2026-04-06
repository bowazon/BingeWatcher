@file:JvmName("BingeWatcherShowCardKt")

package com.bowazon.bingewatcher.core.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import kotlin.text.isNotEmpty


@Composable
fun BingeWatcherShowCard(
    modifier: Modifier = Modifier,
    showName: String,
    pictureUrl: String,
    date: String,
    genre: String,
    rating: String,
    additional: String? = null,
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    // TODO we need to pass Painter in designsystem
    val imageLoader = rememberAsyncImagePainter(
        model = pictureUrl,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )

    Surface(modifier) {
        Box(Modifier.padding(16.dp)) {
            Row {
                Image(
                    modifier = Modifier.size(150.dp),
                    painter = if (!isError) {
                        imageLoader
                    } else {
                        painterResource(R.drawable.ic_launcher_foreground)
                    },
                    contentDescription = null
                )

                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = showName, color = Color.White)
                    additional?.let { Text(text = it, color = Color.White) }
                    Text(text = date, color = Color.White)
                    Text(text = genre, color = Color.White)
                }
            }

            if (rating.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd),
                    verticalAlignment = Alignment.Top
                ) {
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
    }
}