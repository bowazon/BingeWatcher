package com.example.designsystem

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter

@Composable
fun ShowCard(
    modifier: Modifier = Modifier,
    name: String,
    pictureUrl: String,
    date: String,
    genre: String,
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }
    // TODO do we need to load picture here, or pass bitmap
    val imageLoader = rememberAsyncImagePainter(
        model = pictureUrl,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )

    //painterResource(id = R.drawable.ic_launcher_foreground)
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
        Column {
            Text(name)
            Text(date)
            Text(genre)
        }
    }
}