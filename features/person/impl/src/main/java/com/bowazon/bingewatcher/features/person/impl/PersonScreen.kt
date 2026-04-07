package com.bowazon.bingewatcher.features.person.impl

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.size.Size
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherParallaxPicture
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherPersonShortDescription
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowImageWithGradient
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowRestDescription
import com.bowazon.bingewatcher.core.designsystem.utils.getReadableTextColor
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowCard
import com.bowazon.bingewatcher.core.ui.utils.loadPainter
import com.bowazon.bingewatcher.core.designsystem.R

@Composable
fun PersonScreen(
    modifier: Modifier = Modifier,
    viewModel: PersonViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onShowClicked: (Int) -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when(effect) {
                is PersonEffect.OpenShowCard -> {
                    onShowClicked(effect.id)
                }
            }
        }
    }

    when(uiState) {
        is PersonUiState.Loading -> {
            // TODO show loading
        }
        is PersonUiState.Error -> {
            // TODO show error
        }
        is PersonUiState.Success -> {
            PersonScreen(
                modifier = modifier,
                uiData = (uiState as PersonUiState.Success).data,
                onBackClicked = onBackClicked,
                onEvent = viewModel::onEvent,
            )
        }
    }
}

@Composable
internal fun PersonScreen(
    modifier: Modifier = Modifier,
    uiData: PersonUiModel,
    onBackClicked: () -> Unit,
    onEvent: (PersonEvent) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    val painterLargeImage = rememberAsyncImagePainter(model = uiData.personDetailed.image) // TODO actor image

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
                //.background(Color.Black),
            state = lazyListState
        ) {
            item {
                BingeWatcherParallaxPicture(
                    lazyListState = lazyListState,
                    picturePainter = painterLargeImage
                )
            }

            stickyHeader {
                BingeWatcherPersonShortDescription(
                    name = uiData.personDetailed.name,
                    born = uiData.personDetailed.birthday,
                    credits = uiData.creditsSummary
                )
            }

            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Button(onClick = {
                        //onEvent(ShowDetailsEvent.OnFollowClick)
                    }) {
                        // TODO should be like if show ended and follow in ongoing
                        Text("Follow")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            if (uiData.actorCredits.isNotEmpty()) {
                item {
                    Text(
                        text = "Acting career",
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            items(uiData.actorCredits) { actorCredits ->
                val painter = loadPainter(
                    actorCredits.showPicture,
                    painterResource(R.drawable.show_placeholder)
                )
                BingeWatcherShowCard(
                    modifier = Modifier.clickable{
                        Log.i("ART_DBG_4", "here")
                        onEvent(PersonEvent.OnShowClick(actorCredits.showId))
                    },
                    showName = actorCredits.showName,
                    additional = actorCredits.character,
                    picturePainter = painter,
                    date = actorCredits.showPremiered,
                    genre = actorCredits.showGenre,
                    rating = actorCredits.showRating
                )
            }

            if (uiData.crewCredits.isNotEmpty()) {
                item {
                    Text(
                        text = "Crew credits",
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            items(uiData.crewCredits) { crewCredits ->
                val painter = loadPainter(
                    crewCredits.showPicture,
                    painterResource(R.drawable.show_placeholder)
                )
                BingeWatcherShowCard(
                    modifier = Modifier.clickable{
                        onEvent(PersonEvent.OnShowClick(crewCredits.showId))
                    },
                    showName = crewCredits.showName,
                    additional = crewCredits.type, // TODO temp
                    picturePainter = painter,
                    date = crewCredits.showPremiered,
                    genre = crewCredits.showGenre,
                    rating = crewCredits.showRating
                )
            }

        }

        IconButton(
            onClick = { onBackClicked() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = null,
                tint = getReadableTextColor(Color.Black)
            )
        }
    }
}
