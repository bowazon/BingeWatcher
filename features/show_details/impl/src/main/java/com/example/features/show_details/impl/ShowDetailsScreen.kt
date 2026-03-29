package com.example.features.show_details.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.designsystem.BingeWatcherShowDetailedCard
import com.example.designsystem.ShowCard
import com.example.entities.ShowDetailed

@Composable
fun ShowDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: ShowDetailsViewModel = hiltViewModel(),
    showDetailed: ShowDetailed
) {
    Scaffold { padding ->
        BingeWatcherShowDetailedCard(
            name = showDetailed.summary.name,
            rating = showDetailed.summary.rating,
            genres = showDetailed.summary.genres,
            premiered = showDetailed.summary.premiered,
            imdb = showDetailed.imdb,
            ended = showDetailed.ended,
            largeImage = showDetailed.largeImage,
            officialSite = showDetailed.officialSite,
            description = showDetailed.description
        )
    }
}