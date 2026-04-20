package com.bowazon.bingewatcher.features.person.impl

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherBackIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherImdbIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherLikeIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherMediumShowCard
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherOfficialSiteIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherParallaxPicture
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherPersonTopBadge
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShareIcon
import com.bowazon.bingewatcher.core.ui.utils.loadPainter
import com.bowazon.bingewatcher.core.designsystem.R
import com.bowazon.bingewatcher.core.ui.BingeWatcherDetailsScreen

@Composable
fun PersonScreen(
    modifier: Modifier = Modifier,
    viewModel: PersonViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    onShowClicked: (Int) -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when(effect) {
                is PersonEffect.OpenShowCard -> {
                    onShowClicked(effect.id)
                }
            }
        }
    }

    BingeWatcherDetailsScreen(
        modifier = modifier,
        uiState = uiState,
        onBackClicked = onBackClicked,
        largeImageUrl = { it.personDetailed.image },
        header = { uiData ->
            BingeWatcherPersonTopBadge(
                name = uiData.personDetailed.name,
                born = uiData.personDetailed.birthday,
                credits = uiData.creditsSummary
            )
        },
        badge = null,
        actions = { uiData ->
            BingeWatcherLikeIcon(
                onClick = { viewModel.onEvent(PersonEvent.OnFollowClick) },
                isLiked = false
            )

            BingeWatcherShareIcon(onClick = {
                // TODO share
            })
        }
    ) { uiData ->

        if (uiData.actorCredits.isNotEmpty()) {
            item {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = "Acting career",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        items(uiData.actorCredits) { actorCredits ->
            val painter = loadPainter(
                actorCredits.showPicture,
                painterResource(R.drawable.show_placeholder)
            )

            BingeWatcherMediumShowCard(
                //modifier = Modifier.clickable{
                //    Log.i("ART_DBG_4", "here")
                //    viewModel.onEvent(PersonEvent.OnShowClick(actorCredits.showId))
                //},
                showName = actorCredits.showName,
                additional = actorCredits.character,
                picturePainter = painter,
                date = actorCredits.showPremiered,
                genre = actorCredits.showGenre,
                rating = actorCredits.showRating,
                onClick = { viewModel.onEvent(PersonEvent.OnShowClick(actorCredits.showId)) }
            )
        }

        if (uiData.crewCredits.isNotEmpty()) {
            item {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = "Crew credits",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        items(uiData.crewCredits) { crewCredits ->
            val painter = loadPainter(
                crewCredits.showPicture,
                painterResource(R.drawable.show_placeholder)
            )
            BingeWatcherMediumShowCard(
                //modifier = Modifier.clickable{
                //    viewModel.onEvent(PersonEvent.OnShowClick(crewCredits.showId))
                //},
                showName = crewCredits.showName,
                additional = crewCredits.type, // TODO temp
                picturePainter = painter,
                date = crewCredits.showPremiered,
                genre = crewCredits.showGenre,
                rating = crewCredits.showRating,
                onClick = { viewModel.onEvent(PersonEvent.OnShowClick(crewCredits.showId)) }
            )
        }
    }
}
