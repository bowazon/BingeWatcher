package com.bowazon.bingewatcher.features.show.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bowazon.bingewatcher.core.entities.ActorSummary
import com.bowazon.bingewatcher.core.entities.CrewSummary
import com.bowazon.bingewatcher.core.entities.Season
import com.bowazon.bingewatcher.core.entities.ShowDetailed
import com.bowazon.bingewatcher.core.repository.ShowRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ShowDetailsViewModel.Factory::class)
class ShowDetailsViewModel @AssistedInject constructor(
    @Assisted val showId: Int,
    val repository: ShowRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ShowDetailsUiState>(ShowDetailsUiState.Loading)
    val state: StateFlow<ShowDetailsUiState> = _state

    private val _effects = MutableSharedFlow<ShowDetailsEffect>()
    val effects = _effects.asSharedFlow()

    init {
        viewModelScope.launch {
            // TODO exceptions handler? Supervisor Job?
            try {
                coroutineScope {
                    val showDetails = async { repository.getShow(showId) }
                    val cast = async { repository.getActorsForShow(showId) }
                    val crew = async { repository.getCrewForShow(showId) }
                    val pictures = async { repository.getImagesForShow(showId) }
                    val seasons = async { repository.getSeasonsForShow(showId) }

                    val crewMerged = crew
                        .await()
                        .groupBy { crewSummary ->
                            crewSummary.name
                        }.map { (_, personsCrewRoles) ->
                            val first = personsCrewRoles.first()

                            val mergedRoles = personsCrewRoles
                                .map { it.role }
                                .distinct()
                                .joinToString("\n")

                            first.copy(role = mergedRoles)
                        }

                    _state.value = ShowDetailsUiState.Success(
                        ShowDetailsUiModel(
                            showDetails.await(),
                            cast.await(),
                            crewMerged,
                            pictures.await(),
                            seasons.await()
                        )
                    )
                }
            } catch (e: Exception) { // TODO generic error bad
                _state.value = ShowDetailsUiState.Error
            }
        }
    }

    fun onEvent(event: ShowDetailsEvent) {
        when (event) {
            is ShowDetailsEvent.OnImdbClick -> {
                // TODO should have analytics
                val currentState = _state.value
                if (currentState is ShowDetailsUiState.Success) {
                    openUrl(currentState.data.showDetails.imdb)
                }
            }
            is ShowDetailsEvent.OnOfficialSiteClick -> {
                val currentState = _state.value
                if (currentState is ShowDetailsUiState.Success) {
                    openUrl(currentState.data.showDetails.officialSite)
                }
            }
            is ShowDetailsEvent.OnFollowClick -> {
                // TODO
                // to follow repository
            }
            is ShowDetailsEvent.OnSeasonClick -> {
                // TODO showId and seasonId is good place to use inline value classes
                val showId = event.showId
                val seasonId = event.seasonId
                viewModelScope.launch {
                    _effects.emit(ShowDetailsEffect.OpenSeasonCard(
                        showId,
                        seasonId
                    ))
                }
            }
            is ShowDetailsEvent.OnPersonClick -> {
                val personId = event.id
                viewModelScope.launch {
                    _effects.emit(ShowDetailsEffect.OpenPersonCard(personId))
                }
            }
            is ShowDetailsEvent.OnGalleryClick -> {
                val imageIndex = event.imageIndex
                val urlList = event.urlList
                viewModelScope.launch {
                    _effects.emit(ShowDetailsEffect.OpenImageView(imageIndex, urlList))
                }
            }
        }
    }

    private fun openUrl(url: String) {
        viewModelScope.launch {
            _effects.emit(ShowDetailsEffect.OpenUrl(url))
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            showId: Int,
        ): ShowDetailsViewModel
    }
}

sealed class ShowDetailsUiState {
    object Error: ShowDetailsUiState()
    object Loading: ShowDetailsUiState()
    data class Success(val data: ShowDetailsUiModel): ShowDetailsUiState()
}

// TODO dumb model
data class ShowDetailsUiModel(
    val showDetails: ShowDetailed,
    val cast: List<ActorSummary>,
    val crew: List<CrewSummary>,
    val pictures: List<String>,
    val seasons: List<Season>
)

sealed interface ShowDetailsEvent {
    object OnImdbClick : ShowDetailsEvent
    object OnOfficialSiteClick : ShowDetailsEvent
    object OnFollowClick : ShowDetailsEvent
    data class OnSeasonClick(val showId: Int, val seasonId: Int) : ShowDetailsEvent
    data class OnPersonClick(val id: Int) : ShowDetailsEvent
    data class OnGalleryClick(val imageIndex: Int, val urlList: List<String>) : ShowDetailsEvent
}

sealed interface ShowDetailsEffect {
    data class OpenUrl(val url: String) : ShowDetailsEffect
    data class OpenPersonCard(val id: Int) : ShowDetailsEffect
    data class OpenSeasonCard(val showId: Int, val seasonId: Int) : ShowDetailsEffect
    data class OpenImageView(val imageIndex: Int, val urlList: List<String>) : ShowDetailsEffect
}
