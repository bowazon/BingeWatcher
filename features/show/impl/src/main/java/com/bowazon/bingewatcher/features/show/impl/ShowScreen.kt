package com.bowazon.bingewatcher.features.show.impl

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherSmallPersonCard
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowBottomBadge
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherSmallSeasonCard
import androidx.core.net.toUri
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherImdbIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherLikeIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherOfficialSiteIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShareIcon
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherSmallFullCrewCard
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherShowTopBadge
import com.bowazon.bingewatcher.core.designsystem.BingeWatcherSmallImageCard
import com.bowazon.bingewatcher.core.designsystem.R
import com.bowazon.bingewatcher.core.ui.BingeWatcherDetailsScreen
import com.bowazon.bingewatcher.core.ui.utils.loadPainter

@Composable
fun ShowScreen(
    modifier: Modifier = Modifier,
    viewModel: ShowViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
    navigateToPerson: (Int) -> Unit,
    navigateToSeason: (Int, Int) -> Unit,
    navigateToImageView: (Int, List<String>) -> Unit,
    navigateToFullCrewShow: (Int) -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is ShowScreenEffect.OpenUrl -> {
                    val intent = Intent(Intent.ACTION_VIEW, effect.url.toUri())
                    context.startActivity(intent)
                }
                is ShowScreenEffect.OpenPersonScreen -> {
                    navigateToPerson(effect.id)
                }
                is ShowScreenEffect.OpenSeasonScreen -> {
                    navigateToSeason(effect.showId, effect.seasonId)
                }
                is ShowScreenEffect.OpenImageView -> {
                    navigateToImageView(effect.imageIndex, effect.urlList)
                }

                is ShowScreenEffect.OpenFullCrewScreen -> {
                    navigateToFullCrewShow(effect.showId)
                }
            }
        }
    }

    BingeWatcherDetailsScreen(
        modifier = modifier,
        uiState = uiState,
        onBackClicked = onBackClicked,
        largeImageUrl = { it.show.largeImage },
        header = { uiData ->
            BingeWatcherShowTopBadge(
                name = uiData.show.summary.name,
                premiered = uiData.show.summary.premiered,
                rating = uiData.show.summary.rating
            )
        },
        badge = { uiData ->
            BingeWatcherShowBottomBadge(
                genres = uiData.show.summary.genres,
                description = uiData.show.description
            )
        },
        actions = { uiData ->
            BingeWatcherImdbIcon(onClick = { viewModel.onEvent(ShowScreenEvent.OnImdbClick) })

            BingeWatcherLikeIcon(onClick = { viewModel.onEvent(ShowScreenEvent.OnFollowClick) })

            BingeWatcherOfficialSiteIcon( onClick = { viewModel.onEvent(ShowScreenEvent.OnOfficialSiteClick) } )

            BingeWatcherShareIcon(onClick = {
                // TODO share
            })
        },
    ) { uiData ->
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiData.seasons.size) { i ->
                    val painter = loadPainter(uiData.seasons[i].image)

                    BingeWatcherSmallSeasonCard(
                        onClick = { viewModel.onEvent(ShowScreenEvent.OnSeasonClick(
                            uiData.show.id,
                            uiData.seasons[i].id
                        )) },
                        season = i + 1,
                        episodesAmount = uiData.seasons[i].episodesAmount,
                        premiereDate = uiData.seasons[i].premiereDate,
                        imagePainter = painter
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        item {
            if(uiData.cast.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = "Cast"
                )
            }
        }

        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiData.cast) { actor ->
                    val painter = loadPainter(actor.image, painterResource(R.drawable.person_placeholder))

                    BingeWatcherSmallPersonCard(
                        onClick = { viewModel.onEvent(ShowScreenEvent.OnPersonClick(actor.id)) },
                        personName = actor.name,
                        imagePainter = painter
                    )
                }
                item {
                    BingeWatcherSmallFullCrewCard(
                        onClick = { viewModel.onEvent(ShowScreenEvent.OnFullCrewClick(uiData.show.id)) },
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        /*
        item {
            if(uiData.crew.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = "Crew"
                )
            }
        }

        // TODO showing crew this way is pain in the ass. Do we need it here? Should we do separate full crew feature, like in IMDB?
        item {
            val maxLines = uiData.crew.maxOfOrNull { it.role.lines().size } ?: 1
            val lineHeight = 20.dp
            val infoHeight = lineHeight * maxLines

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiData.crew) { crewMember ->
                    val painter = loadPainter(crewMember.image, painterResource(R.drawable.person_placeholder))

                    BingeWatcherSmallPersonCard(
                        onClick = { viewModel.onEvent(ShowScreenEvent.OnPersonClick(crewMember.id)) },
                        personName = crewMember.name,
                        additionalInfo = crewMember.role,
                        additionalInfoHeight = infoHeight,
                        imagePainter = painter
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
         */

        item {
            if (uiData.pictures.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    text = "Gallery"
                )
            }
        }

        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiData.pictures.size) { i ->
                    val painter = loadPainter(uiData.pictures[i])

                    BingeWatcherSmallImageCard(
                        painter = painter,
                        onClick = {
                            viewModel.onEvent(ShowScreenEvent.OnGalleryClick(i, uiData.pictures))
                        })
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
