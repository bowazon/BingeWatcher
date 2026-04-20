package com.bowazon.bingewatcher.features.fullcrew.impl

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherLikeIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherMediumEpisodeCard
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherMediumPersonCard
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherMediumShowCard
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherPersonTopBadge
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShareIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowTopBadge
import com.bowazon.bingewatcher.core.designsystem.R
import com.bowazon.bingewatcher.core.entities.Episode
import com.bowazon.bingewatcher.core.entities.ShowSummary
import com.bowazon.bingewatcher.core.repository.FullCrewRequest
import com.bowazon.bingewatcher.core.ui.BingeWatcherDetailsScreen
import com.bowazon.bingewatcher.core.ui.utils.loadPainter

@Composable
fun FullCrewScreen(
    modifier: Modifier = Modifier,
    viewModel: FullCrewViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    navigateToPerson: (Int) -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when(effect) {
                is FullCrewEffect.OpenPersonScreen -> {
                    navigateToPerson(effect.id)
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
            when(uiData.fullCrew.ofWhat) {
                is ShowSummary -> {
                    BingeWatcherShowTopBadge(
                        name = (uiData.fullCrew.ofWhat as ShowSummary).name,
                        premiered = (uiData.fullCrew.ofWhat as ShowSummary).premiered,
                        rating = (uiData.fullCrew.ofWhat as ShowSummary).rating
                    )
                }
                is Episode -> {
                    //BingeWatcherMediumEpisodeCard()
                }
            }

        },
        badge = null,
        actions = { }
    ) { uiData ->

        items(uiData.fullCrew.crew) { crewMember ->
            val painter = loadPainter(
                crewMember.image,
                painterResource(R.drawable.person_placeholder)
            )

            BingeWatcherMediumPersonCard(
                name = crewMember.name,
                picturePainter = painter,
                role = crewMember.role,
                onClick = { viewModel.onEvent(FullCrewEvent.OnPersonClick(crewMember.id)) }
            )
        }
    }
}