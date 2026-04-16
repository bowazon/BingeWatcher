package com.bowazon.bingewatcher.features.fullcrew.impl

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherLikeIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherMediumPersonCard
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherPersonTopBadge
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShareIcon
import com.bowazon.bingewatcher.core.designsystem.R
import com.bowazon.bingewatcher.core.ui.BingeWatcherDetailsScreen
import com.bowazon.bingewatcher.core.ui.utils.loadPainter

@Composable
fun FullCrewScreen(
    modifier: Modifier = Modifier,
    viewModel: FullCrewViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when(effect) {
                is FullCrewEffect.OpenShowCard -> {

                }
            }
        }
    }

    BingeWatcherDetailsScreen(
        modifier = modifier,
        uiState = uiState,
        onBackClicked = onBackClicked,
        largeImageUrl = { null },
        header = { uiData ->

        },
        badge = null,
        actions = { }
    ) { uiData ->

        items(uiData.crew) { crewMember ->
            val painter = loadPainter(
                crewMember.image,
                painterResource(R.drawable.show_placeholder)
            )

            BingeWatcherMediumPersonCard(
                name = crewMember.name,
                picturePainter = painter,
                role = crewMember.role
            ) // TODO click
        }
    }
}