@file:JvmName("BingeWatcherShowCardKt")

package com.bowazon.bingewatcher.core.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
    characterName: String? = null,
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

    Surface(
        modifier,
        //color = Color.Black
    ) {
        Row(Modifier.padding(16.dp)) {
            Image(
                modifier = Modifier.size(150.dp),
                // TODO no need for image loader, change for AsyncImage from coil
                painter = if (isError.not()) {
                    imageLoader
                } else {
                    // TODO image for error
                    painterResource(R.drawable.ic_launcher_foreground)
                },
                contentDescription = null
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = showName, color = Color.White)
                    characterName?.let { Text(text = it, color = Color.White) }
                    Text(text = date, color = Color.White)
                    Text(text = genre, color = Color.White)
                }
                // TODO this block repeats! Should be separate component
                if (rating.isNotEmpty()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
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
}